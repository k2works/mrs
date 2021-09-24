locals {
  name                        = "${var.org_name}${var.vpc_name}${var.app_name}"
  environment_upper           = upper(var.environment)
  group_name                  = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  api_url                     = "https://${local.subdomain_name}.${var.domain}/api"
  ecr_repository_name         = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}"
  ecr_repository_url          = "${var.ecr_url}/${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}"
  alb_log_bucket_name         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-alb-log"
  deploy_bucket_name          = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy"
  operation_bucket_name       = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-operation"
  cloudwatch_logs_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-cloudwathc-logs"
  subdomain_name              = lower(var.app_name)
  lb_name                     = lower(var.app_name)
  service_name                = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}"
  codebuild_name              = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}_build"
  codepipeline_name           = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}_pipline"
  stream_name                 = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}_stream"
  log_filter_name             = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}_log_filter"
}

provider "aws" {
  profile = var.provider_config.profile
  region  = var.provider_config.regions
  default_tags {
    tags = {
      Name        = local.name
      Environment = var.environment
    }
  }
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 1.0.6"

  backend "s3" {
    bucket  = "mrs-org-mrs-production-tfstate"
    region  = "ap-northeast-1"
    profile = "k2works"
    key     = "terraform.tfstate"
    encrypt = true
  }
}

resource "random_password" "password" {
  length           = 16
  special          = true
  override_special = "_%-"
}

# 管理
data "aws_iam_policy" "ec2_for_ssm" {
  arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

module "ec2_for_ssm_role" {
  source     = "./iam_role"
  name       = "ec2-for-ssm"
  identifier = "ec2.amazonaws.com"
  policy     = data.aws_iam_policy_document.ec2_for_ssm.json
}

resource "aws_iam_instance_profile" "ec2_for_ssm" {
  name = "ec2-for-ssm"
  role = module.ec2_for_ssm_role.iam_role_name
}

data "aws_iam_policy_document" "ec2_for_ssm" {
  source_json = data.aws_iam_policy.ec2_for_ssm.policy

  statement {
    effect    = "Allow"
    resources = ["*"]

    actions = [
      "s3:PutObject",
      "log:PutLogEvents",
      "logs:CreateLogStream",
      "ecr:GetAuthorizationToken",
      "ecr:BatchCheckLayerAvailability",
      "ecr:GetDownloadUrlForLayer",
      "ecr:BatchGetImage",
      "ssm:GetParameter",
      "ssm:GetParameters",
      "ssm:GetParametersByPath",
      "kms:Decrypt",
    ]
  }
}

module "app_management_ec2" {
  source               = "./management/ec2"
  ami                  = var.images.ap-northeast-1
  iam_instance_profile = aws_iam_instance_profile.ec2_for_ssm.name
  operation_bucket_id  = module.app_storage.operation_bucket_id
  subnet_id            = module.app_network_vpc.vpc_subnet_private-a_id
}

module "app_management_group" {
  source      = "./management/resource_groups"
  group_name  = local.group_name
  environment = var.environment
}

module "app_management_ssm_parameter" {
  source      = "./management/ssm_parameter"
  environment = local.environment_upper
  environment_variables = {
    SPRING_PROFILES_ACTIVE     = "aws-mysql"
    SPRING_FLYWAY_SCHEMAS      = module.app_database_serverless_mysql.rds_dbname
    SPRING_DATASOURCE_USERNAME = module.app_database_serverless_mysql.rds_username
    SPRING_DATASOURCE_PASSWORD = module.app_database_serverless_mysql.rds_password
    SPRING_DATASOURCE_URL      = "jdbc:mysql://${module.app_database_serverless_mysql.rds_hostname}:${module.app_database_serverless_mysql.rds_port}/${module.app_database_serverless_mysql.rds_dbname}?useSSL=false"
    RDS_HOSTNAME               = module.app_database_serverless_mysql.rds_hostname
    RDS_PORT                   = module.app_database_serverless_mysql.rds_port
    API_URL                    = local.api_url
    ECR_REPOSITORY_NAME        = local.ecr_repository_name
    ECR_REPOSITORY_URL         = local.ecr_repository_url
    AWS_DEFAULT_REGION         = var.provider_config.regions
    DOCKERHUB_USER             = var.dockerhub_user
    DOCKERHUB_PASS             = var.dockerhub_pass
  }
}

# ストレージ
module "app_storage" {
  source                      = "./storage"
  domain                      = var.domain
  log_bucket_name             = local.alb_log_bucket_name
  deploy_bucket_name          = local.deploy_bucket_name
  operation_bucket_name       = local.operation_bucket_name
  cloudwatch_logs_bucket_name = local.cloudwatch_logs_bucket_name
}

# ネットワーク
module "app_network_vpc" {
  source   = "./network/vpc"
  org_name = var.org_name
  vpc_name = var.vpc_name
  app_name = var.app_name
}

# セキュリティグループ
module "app_security_group_ssh" {
  source      = "./security_group"
  name        = "ssh-sg"
  vpc_id      = module.app_network_vpc.vpc_id
  port        = 22
  cidr_blocks = ["0.0.0.0/0"]
}

module "app_security_group_http" {
  source      = "./security_group"
  name        = "http-sg"
  vpc_id      = module.app_network_vpc.vpc_id
  port        = 80
  cidr_blocks = ["0.0.0.0/0"]
}

module "app_security_group_https" {
  source      = "./security_group"
  name        = "https-sg"
  vpc_id      = module.app_network_vpc.vpc_id
  port        = 443
  cidr_blocks = ["0.0.0.0/0"]
}

module "app_security_group_http_redirect" {
  source      = "./security_group"
  name        = "http-redirect-sg"
  vpc_id      = module.app_network_vpc.vpc_id
  port        = 8080
  cidr_blocks = ["0.0.0.0/0"]
}

module "app_security_group_app_service" {
  source      = "./security_group"
  name        = "app-service-sg"
  vpc_id      = module.app_network_vpc.vpc_id
  port        = 5000
  cidr_blocks = ["0.0.0.0/0"]
}

# ロードバランサーとDNS
module "app_network_cert" {
  source     = "./network/cert"
  domain     = var.domain
  sub_domain = local.subdomain_name
}

module "app_network_dns" {
  source                    = "./network/dns"
  domain                    = var.domain
  sub_domain                = local.subdomain_name
  dns_name                  = module.app_network_loadbalancer.alb_dns_name
  alb_zone_id               = module.app_network_loadbalancer.alb_zone_id
  domain_validation_options = module.app_network_cert.domain_validation_options
}

module "app_network_loadbalancer" {
  source              = "./network/lb"
  name                = local.lb_name
  subnet_public_a     = module.app_network_vpc.vpc_subnet_public-a
  subnet_public_c     = module.app_network_vpc.vpc_subnet_public-c
  alb_log_bucket      = module.app_storage.alb_log
  http_sg             = module.app_security_group_http
  https_sg            = module.app_security_group_https
  http_redirect_sg    = module.app_security_group_http_redirect
  acm_certificate_arn = module.app_network_cert.acm_certificate_arn
  vpc_id              = module.app_network_vpc.vpc_id
}

# コンテナオーケストレーション
data "aws_iam_policy" "ecs_task_execution_role_policy" {
  arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

data "aws_iam_policy_document" "ecs_task_execution" {
  source_json = data.aws_iam_policy.ecs_task_execution_role_policy.policy

  statement {
    effect    = "Allow"
    actions   = ["ssm:GetParameters", "kms:Decrypt"]
    resources = ["*"]
  }
}

module "ecs_task_execution_role" {
  source     = "./iam_role"
  name       = "ecs-task-execution"
  identifier = "ecs-tasks.amazonaws.com"
  policy     = data.aws_iam_policy_document.ecs_task_execution.json
}

module "app_container_service" {
  source                  = "./container"
  name                    = local.service_name
  subnet_private_a        = module.app_network_vpc.vpc_subnet_private-a
  subnet_private_c        = module.app_network_vpc.vpc_subnet_private-c
  lb_target_group         = module.app_network_loadbalancer.alb_target_group
  app_service_sg          = module.app_security_group_app_service
  ecs_task_execution_role = module.ecs_task_execution_role
  api_url                 = local.api_url
  environment_variables = {
    SPRING_PROFILES_ACTIVE     = "/${local.environment_upper}/SPRING_PROFILES_ACTIVE"
    SPRING_FLYWAY_SCHEMAS      = "/${local.environment_upper}/SPRING_FLYWAY_SCHEMAS"
    SPRING_DATASOURCE_USERNAME = "/${local.environment_upper}/SPRING_DATASOURCE_USERNAME"
    SPRING_DATASOURCE_PASSWORD = "/${local.environment_upper}/SPRING_DATASOURCE_PASSWORD"
    SPRING_DATASOURCE_URL      = "/${local.environment_upper}/SPRING_DATASOURCE_URL"
  }
}

# データストア
module "app_database_serverless_mysql" {
  source                    = "./database/rds/aurora/mysql"
  app_name                  = var.app_name
  app_env_name              = "${lower(var.app_name)}-${lower(var.environment)}"
  subnet_id_1               = module.app_network_vpc.vpc_subnet_private-a_id
  subnet_id_2               = module.app_network_vpc.vpc_subnet_private-c_id
  vpc_id                    = module.app_network_vpc.vpc_id
  security_group_id         = module.app_database_serverless_mysql.security_group_id
  identifier                = "mrsmysql"
  engine                    = "aurora-mysql"
  engine_version            = "5.7"
  db_name                   = "appdb"
  username                  = var.db_mysql_username
  db_password               = random_password.password.result
  db_parameter_group_family = "aurora-mysql5.7"
}

# デプロイメントパイプライン
data "aws_iam_policy_document" "codebuild" {
  statement {
    effect    = "Allow"
    resources = ["*"]

    actions = [
      "s3:PutObject",
      "s3:GetObject",
      "s3:GetObjectVersion",
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents",
      "ecr:GetAuthorizationToken",
      "ecr:BatchCheckLayerAvailability",
      "ecr:GetDownloadUrlForLayer",
      "ecr:GetRepositoryPolicy",
      "ecr:DescribeRepositories",
      "ecr:ListImages",
      "ecr:DescribeImages",
      "ecr:BatchGetImage",
      "ecr:InitiateLayerUpload",
      "ecr:UploadLayerPart",
      "ecr:CompleteLayerUpload",
      "ecr:PutImage",
      "ssm:GetParameters",
      "ssm:GetParameter",
    ]
  }
}

module "codebuild_role" {
  source     = "./iam_role"
  name       = "codebuild"
  identifier = "codebuild.amazonaws.com"
  policy     = data.aws_iam_policy_document.codebuild.json
}

data "aws_iam_policy_document" "codepipeline" {
  statement {
    effect    = "Allow"
    resources = ["*"]

    actions = [
      "s3:PutObject",
      "s3:GetObject",
      "s3:GetObjectVersion",
      "s3:GetBucketVersioning",
      "s3:PutObjectAcl",
      "codebuild:BatchGetBuilds",
      "codebuild:StartBuild",
      "ecs:DescribeServices",
      "ecs:DescribeTaskDefinition",
      "ecs:DescribeTasks",
      "ecs:ListTasks",
      "ecs:RegisterTaskDefinition",
      "ecs:UpdateService",
      "iam:PassRole",
      "codestar-connections:UseConnection",
    ]
  }
}

module "codepipeline_role" {
  source     = "./iam_role"
  name       = "codepipeline"
  identifier = "codepipeline.amazonaws.com"
  policy     = data.aws_iam_policy_document.codepipeline.json
}

module "app_ci_codebuild" {
  source       = "./ci/codebuild"
  name         = local.codebuild_name
  iam_role_arn = module.codebuild_role.iam_role_arn
  buildspec    = "buildspec-ecs.yml"
}

module "app_ci_codepipeline" {
  source                 = "./ci/codepipeline"
  name                   = local.codepipeline_name
  codebuild_project_name = module.app_ci_codebuild.project_id
  deploy_bucket_name     = local.deploy_bucket_name
  ecs_cluster_name       = module.app_container_service.ecs_cluster_name
  ecs_service_name       = module.app_container_service.ecs_service_name
  full_repository_id     = "k2works/mrs"
  blanch_name            = "develop"
  role_arn               = module.codepipeline_role.iam_role_arn
}
# ロギング
data "aws_iam_policy_document" "kinesis_data_firehose" {
  statement {
    effect = "Allow"

    actions = [
      "s3:AbortMultipartUpload",
      "s3:GetBucketLocation",
      "s3:GetObject",
      "s3:ListBucket",
      "s3:ListBucketMultipartUploads",
      "s3:PutObject",
    ]

    resources = [
      "arn:aws:s3:::${module.app_storage.cloudwatch_logs_bucket.id}",
      "arn:aws:s3:::${module.app_storage.cloudwatch_logs_bucket.id}/*",
    ]
  }
}

module "kinesis_data_firehose_role" {
  source     = "./iam_role"
  name       = "kinesis-data-firehose"
  identifier = "firehose.amazonaws.com"
  policy     = data.aws_iam_policy_document.kinesis_data_firehose.json
}

data "aws_iam_policy_document" "cloudwatch_logs" {
  statement {
    effect    = "Allow"
    actions   = ["firehose:*"]
    resources = ["arn:aws:firehose:ap-northeast-1:*:*"]
  }

  statement {
    effect    = "Allow"
    actions   = ["iam:PassRole"]
    resources = ["arn:aws:iam::*:role/cloudwatch-logs"]
  }
}

module "cloudwatch_logs_role" {
  source     = "./iam_role"
  name       = "cloudwatch-logs"
  identifier = "logs.ap-northeast-1.amazonaws.com"
  policy     = data.aws_iam_policy_document.cloudwatch_logs.json
}

module "app_logging" {
  source                        = "./logging"
  kinesis_name                  = local.stream_name
  kinesis_role_arn              = module.kinesis_data_firehose_role.iam_role_arn
  kinesis_bucket_arn            = module.app_storage.cloudwatch_logs_bucket.arn
  kinesis_prefix                = "ecs-scheduled-tasks/${local.service_name}/"
  cloudwatch_log_name           = local.log_filter_name
  cloudwatch_log_log_group_name = module.app_container_service.log_group.name
  cloudwatch_log_role_arn       = module.cloudwatch_logs_role.iam_role_arn
}

