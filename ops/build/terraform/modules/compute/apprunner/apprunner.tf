variable "org_name" {}
variable "app_name" {}
variable "iam_role_name" {}
variable "iam_policy_name" {}
variable "environment_variables" {}

################################################################################
# ECR                                                                          #
################################################################################
resource "aws_ecr_repository" "app" {
  name = "${var.org_name}/${var.app_name}"
}

data "aws_ecr_authorization_token" "token" {}

resource "null_resource" "image_push" {
  provisioner "local-exec" {
    command = "cd ../../../ && ./gradlew build -x test -Penv=production"
  }
  provisioner "local-exec" {
    command = "cd ../../../ && docker build -f ops/build/docker/java/Dockerfile -t ${aws_ecr_repository.app.repository_url}:latest ."
  }
  provisioner "local-exec" {
    command = "docker login -u AWS -p ${data.aws_ecr_authorization_token.token.password} ${data.aws_ecr_authorization_token.token.proxy_endpoint}"
  }
  provisioner "local-exec" {
    command = "docker push ${aws_ecr_repository.app.repository_url}"
  }
}

output "docker_push" {
  value = "docker push ${aws_ecr_repository.app.repository_url}"
}

################################################################################
# App Runner                                                                   #
################################################################################
resource "aws_apprunner_service" "app" {
  depends_on = [null_resource.image_push]

  service_name = var.app_name

  source_configuration {
    image_repository {
      image_repository_type = "ECR"
      image_identifier      = "${aws_ecr_repository.app.repository_url}:latest"

      image_configuration {
        port = "5000"
        runtime_environment_variables = {
          SPRING_PROFILES_ACTIVE     = var.environment_variables.SPRING_PROFILES_ACTIVE
          SPRING_FLYWAY_SCHEMAS      = var.environment_variables.SPRING_FLYWAY_SCHEMAS
          SPRING_DATASOURCE_USERNAME = var.environment_variables.SPRING_DATASOURCE_USERNAME
          SPRING_DATASOURCE_PASSWORD = var.environment_variables.SPRING_DATASOURCE_PASSWORD
          SPRING_DATASOURCE_URL      = var.environment_variables.SPRING_DATASOURCE_URL
        }
      }
    }

    authentication_configuration {
      access_role_arn = aws_iam_role.apprunner.arn
    }
  }
}

######################################################################
# IAM Role for App Runner                                            #
######################################################################
resource "aws_iam_role" "apprunner" {
  name               = var.iam_role_name
  assume_role_policy = data.aws_iam_policy_document.apprunner_assume.json
}

data "aws_iam_policy_document" "apprunner_assume" {
  statement {
    effect = "Allow"

    actions = [
      "sts:AssumeRole",
    ]

    principals {
      type = "Service"
      identifiers = [
        "build.apprunner.amazonaws.com",
      ]
    }
  }
}

resource "aws_iam_role_policy_attachment" "apprunner" {
  role       = aws_iam_role.apprunner.name
  policy_arn = aws_iam_policy.apprunner_custom.arn
}

resource "aws_iam_policy" "apprunner_custom" {
  name   = var.iam_policy_name
  policy = data.aws_iam_policy_document.apprunner_custom.json
}

data "aws_iam_policy_document" "apprunner_custom" {
  statement {
    effect = "Allow"

    actions = [
      "ecr:GetAuthorizationToken",
      "ecr:BatchCheckLayerAvailability",
      "ecr:GetDownloadUrlForLayer",
      "ecr:BatchGetImage",
      "ecr:DescribeImages",
    ]

    resources = [
      "*",
    ]
  }
}
