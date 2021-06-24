variable "deploy_bucket_arn" {}
variable "deploy_bucket_name" {}
variable "cert_bucket_arn" {}
variable "project_name" {}
variable "project_description" {}
variable "source_type" {}
variable "source_location" {}
variable "source_version" {}

data "aws_caller_identity" "iam" {}
variable "region" {}
variable "vpc_id" {}
variable "public_subnet_arn" {}
variable "public_subnet_id" {}
variable "private_subnet_arn" {}
variable "private_subnet_id" {}
variable "security_group_id" {}

resource "aws_s3_bucket" "cache_bucket" {
  bucket = "${var.deploy_bucket_name}-cache"
  acl = "private"
}

resource "aws_iam_role" "app_role" {
  name = "codebuild_app_role"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "codebuild.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "app_role_policy" {
  role = aws_iam_role.app_role.name

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Resource": [
        "*"
      ],
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "ec2:CreateNetworkInterface",
        "ec2:DescribeDhcpOptions",
        "ec2:DescribeNetworkInterfaces",
        "ec2:DeleteNetworkInterface",
        "ec2:DescribeSubnets",
        "ec2:DescribeSecurityGroups",
        "ec2:DescribeVpcs"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "ec2:CreateNetworkInterfacePermission"
      ],
      "Resource": [
        "arn:aws:ec2:${var.region}:${data.aws_caller_identity.iam.account_id}:network-interface/*"
      ],
      "Condition": {
        "StringEquals": {
          "ec2:Subnet": [
            "${var.public_subnet_arn}",
            "${var.private_subnet_arn}"
          ],
          "ec2:AuthorizedService": "codebuild.amazonaws.com"
        }
      }
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:*"
      ],
      "Resource": [
        "${var.deploy_bucket_arn}",
        "${var.deploy_bucket_arn}/*",
        "${var.cert_bucket_arn}",
        "${var.cert_bucket_arn}/*",
        "${aws_s3_bucket.cache_bucket.arn}",
        "${aws_s3_bucket.cache_bucket.arn}/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "ssm:GetParameters",
        "ssm:GetParameter"
      ],
      "Resource": [
        "*"
      ]
    }
  ]
}
POLICY
}

resource "aws_codebuild_project" "app" {
  name = var.project_name
  description = var.deploy_bucket_name
  build_timeout = "10"
  service_role = aws_iam_role.app_role.arn

  artifacts {
    type = "S3"
    name = "app.zip"
    packaging = "ZIP"
    location = var.deploy_bucket_name
  }

  cache {
    type = "S3"
    location = aws_s3_bucket.cache_bucket.bucket
  }

  environment {
    compute_type = "BUILD_GENERAL1_SMALL"
    image = "aws/codebuild/standard:5.0"
    type = "LINUX_CONTAINER"
    image_pull_credentials_type = "CODEBUILD"

    environment_variable {
      name = "SOME_KEY1"
      value = "SOME_VALUE1"
    }
  }

  logs_config {
    cloudwatch_logs {
      group_name = "log-group"
      stream_name = "log-stream"
    }

    s3_logs {
      status = "ENABLED"
      location = "${aws_s3_bucket.cache_bucket.id}/build-log"
    }
  }

  source {
    type = var.source_type
    location = var.source_location
    git_clone_depth = 1

    git_submodules_config {
      fetch_submodules = true
    }
  }

  source_version = var.source_version
}
