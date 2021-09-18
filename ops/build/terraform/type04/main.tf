locals {
  name                = "${var.org_name}${var.vpc_name}${var.app_name}"
  service_name        = "${lower(var.org_name)}_${lower(var.vpc_name)}_${lower(var.app_name)}"
  lb_name             = lower(var.app_name)
  subdomain_name      = lower(var.app_name)
  group_name          = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  alb_log_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-alb-log"
  deploy_bucket_name  = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy"
  api_url             = "https://${local.subdomain_name}.${var.domain}/api"
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

data "aws_iam_policy_document" "codedeploy_role" {
  statement {
    effect = "Allow"
    actions = [
      "autoscaling:CompleteLifecycleAction",
      "autoscaling:DeleteLifecycleHook",
      "autoscaling:DescribeAutoScalingGroups",
      "autoscaling:DescribeLifecycleHooks",
      "autoscaling:PutLifecycleHook",
      "autoscaling:RecordLifecycleActionHeartbeat",
      "ec2:DescribeInstances",
      "ec2:DescribeInstanceStatus",
      "tag:GetTags",
      "tag:GetResources",
    ]

    resources = ["*"]
  }
}

module "codedeploy_role" {
  source     = "./iam_role"
  name       = "codedeploy_role"
  identifier = "codedeploy.amazonaws.com"
  policy     = data.aws_iam_policy_document.codedeploy_role.json
}

module "app_storage" {
  source             = "./storage"
  domain             = var.domain
  log_bucket_name    = local.alb_log_bucket_name
  deploy_bucket_name = local.deploy_bucket_name
}

module "app_network_vpc" {
  source   = "../modules/network/vpc"
  org_name = var.org_name
  vpc_name = var.vpc_name
  app_name = var.app_name
}

module "app_network_cert" {
  source     = "../modules/network/cert"
  domain     = var.domain
  sub_domain = local.subdomain_name
}

module "app_network_dns" {
  source                    = "../modules/network/dns"
  domain                    = var.domain
  sub_domain                = local.subdomain_name
  dns_name                  = module.app_network_loadbalancer.alb_dns_name
  alb_zone_id               = module.app_network_loadbalancer.alb_zone_id
  domain_validation_options = module.app_network_cert.domain_validation_options
}

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

module "app_network_loadbalancer" {
  source              = "../modules/network/lb"
  name                = local.lb_name
  subnet_public_a     = module.app_network_vpc.vpc_subnet_public-a
  subnet_public_c     = module.app_network_vpc.vpc_subnet_public-c
  alb_log_bucket      = module.app_storage.alb_log
  http_sg             = module.app_security_group_http
  https_sg            = module.app_security_group_https
  http_redirect_sg    = module.app_security_group_http_redirect
  acm_certificate_arn = module.app_network_cert.acm_certificate_arn
  vpc_id              = module.app_network_vpc.vpc_id
  target_id           = "10.0.0.10"
}

module "nginx_sg" {
  source      = "./security_group"
  name        = "nginx-sg"
  vpc_id      = module.app_network_vpc.vpc_id
  port        = 80
  cidr_blocks = [module.app_network_vpc.vpc_cidr]
}

module "app_container_service" {
  source                  = "./container"
  name                    = local.service_name
  subnet_private_a        = module.app_network_vpc.vpc_subnet_private-a
  subnet_private_c        = module.app_network_vpc.vpc_subnet_private-c
  lb_target_group         = module.app_network_loadbalancer.alb_target_group
  nginx_sg                = module.nginx_sg
  ecs_task_execution_role = module.ecs_task_execution_role
  api_url                 = local.api_url
}

module "app_database_serverless_mysql" {
  source = "../modules/database/rds/aurora/mysql"

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
  db_parameter_group_family = "mysql5.7"
}

module "app_management_ssm_parameter" {
  source      = "../modules/management/ssm_parameter"
  environment = upper(var.environment)
  environment_variables = {
    SPRING_FLYWAY_SCHEMAS      = module.app_database_serverless_mysql.rds_dbname
    SPRING_DATASOURCE_USERNAME = module.app_database_serverless_mysql.rds_username
    SPRING_DATASOURCE_PASSWORD = module.app_database_serverless_mysql.rds_password
    SPRING_DATASOURCE_URL      = "jdbc:mysql://${module.app_database_serverless_mysql.rds_hostname}:${module.app_database_serverless_mysql.rds_port}/${module.app_database_serverless_mysql.rds_dbname}"
    RDS_HOSTNAME               = module.app_database_serverless_mysql.rds_hostname
    RDS_PORT                   = module.app_database_serverless_mysql.rds_port
    API_URL                    = local.api_url
  }
}

module "app_management_group" {
  source      = "../modules/management/resource_groups"
  group_name  = local.group_name
  environment = var.environment
}

module "app_management_codedeploy" {
  source           = "./ci/codedeploy"
  app_name         = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_group_name   = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  service_role_arn = module.codedeploy_role.iam_role_arn
}

module "app_management_codebuild" {
  source              = "./ci/codebuild"
  project_name        = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  project_description = "CodeBuildProject"
  vpc_id              = module.app_network_vpc.vpc_id
  public_subnet_arn   = module.app_network_vpc.vpc_subnet_public-a_arn
  public_subnet_id    = module.app_network_vpc.vpc_subnet_public-a_id
  private_subnet_arn  = module.app_network_vpc.vpc_subnet_private-c_arn
  private_subnet_id   = module.app_network_vpc.vpc_subnet_private-c_id
  region              = "ap-northeast-1"
  deploy_bucket_name  = local.deploy_bucket_name
  deploy_bucket_arn   = module.app_storage.deploy_bucket_arn
  source_type         = "GITHUB"
  source_location     = "https://github.com/k2works/mrs.git"
  source_version      = "develop"
}

module "app_management_codepipeline" {
  source                             = "./ci/codepipeline"
  name                               = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-pipeline"
  deploy_bucket_name                 = local.deploy_bucket_name
  full_repository_id                 = "k2works/mrs"
  blanch_name                        = "develop"
  code_build_project_name            = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  code_deploy_application_name       = "${var.org_name}${var.vpc_name}${var.app_name}"
  code_deploy_application_group_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
}
