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

locals {
  name           = "${var.org_name}${var.vpc_name}${var.app_name}"
  bucket_name    = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
  subdomain_name = lower(var.app_name)
  group_name     = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  api_url        = "https://${local.subdomain_name}.${var.domain}/api"
  app_domain     = "app-runner.${var.domain}"
}

# 管理
resource "random_password" "password" {
  length           = 16
  special          = true
  override_special = "_%-"
}

module "app_management_group" {
  source      = "./management/resource_groups"
  group_name  = local.group_name
  environment = var.environment
}

# ストレージ

# ネットワーク
module "app_network_vpc" {
  source   = "./network/vpc"
  org_name = var.org_name
  vpc_name = var.vpc_name
  app_name = var.app_name
}

# コンピューティング
module "app_compute_apprunner" {
  source          = "./compute/apprunner"
  org_name        = lower(var.org_name)
  app_name        = lower(var.app_name)
  iam_policy_name = "apprunner_iam_policy"
  iam_role_name   = "apprunner_iam_role"
  domain          = var.domain
  app_domain      = local.app_domain
  environment_variables = {
    SPRING_PROFILES_ACTIVE     = "aws-postgres"
    SPRING_FLYWAY_SCHEMAS      = module.app_database_postgres.rds_dbname
    SPRING_DATASOURCE_USERNAME = module.app_database_postgres.rds_username
    SPRING_DATASOURCE_PASSWORD = module.app_database_postgres.rds_password
    SPRING_DATASOURCE_URL      = "jdbc:postgresql://${module.app_database_postgres.rds_hostname}:${module.app_database_postgres.rds_port}/${module.app_database_postgres.rds_dbname}"
  }
}

# データストア
module "app_database_postgres" {
  source = "./database/rds/postgres"

  app_name                  = var.app_name
  app_env_name              = "${lower(var.app_name)}-${lower(var.environment)}"
  subnet_id_1               = module.app_network_vpc.vpc_subnet_public-a_id
  subnet_id_2               = module.app_network_vpc.vpc_subnet_public-c_id
  vpc_id                    = module.app_network_vpc.vpc_id
  security_group_id         = module.app_database_postgres.security_group_id
  identifier                = "mrspostgres"
  instance_class            = "db.t2.small"
  allocated_storage         = "5"
  engine                    = "postgres"
  engine_version            = "10.5"
  db_name                   = "appdb"
  username                  = var.db_postgres_username
  db_password               = random_password.password.result
  db_parameter_group_family = "postgres10"
}

# デプロイメントパイプライン

# モバイル
module "app_mobile_amplify" {
  source         = "./mobile/amplify"
  app_api_url    = "https://${module.app_compute_apprunner.service_url}/api"
  app_name       = var.app_name
  app_repository = "https://github.com/k2works/mrs"
  access_token   = var.github_personal_access_token
  domain         = var.domain
}

