locals {
  name           = "${var.org_name}${var.vpc_name}${var.app_name}"
  bucket_name    = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
  subdomain_name = lower(var.app_name)
  group_name     = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  api_url        = "https://${local.subdomain_name}.${var.domain}/api"
}

provider "aws" {
  profile = var.provider_config.profile
  region  = var.provider_config.regions
  default_tags {
    tags = {
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

data "aws_iam_policy_document" "allow_describe_regions" {
  statement {
    effect  = "Allow"
    actions = ["ec2:DescribeRegions"]

    resources = ["*"]
  }
}

module "describe_regions_for_ec2" {
  source     = "./iam_role"
  name       = "describe-regions-for-ec2"
  identifier = "ec2.amazonaws.com"
  policy     = data.aws_iam_policy_document.allow_describe_regions.json
}

data "aws_iam_policy_document" "ec2_role" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole", "s3:*"]

    resources = ["*"]
  }
}

module "ec2_role" {
  source     = "./iam_role"
  name       = "ec2_role"
  identifier = "ec2.amazonaws.com"
  policy     = data.aws_iam_policy_document.ec2_role.json
}

resource "aws_iam_role_policy_attachment" "ec2-ssm-core-attach" {
  role       = module.ec2_role.iam_role_name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_role_policy_attachment" "ec2-ssm-patch-attach" {
  role       = module.ec2_role.iam_role_name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMPatchAssociation"
}

resource "aws_iam_role_policy_attachment" "elastic-beanstalk-web-tier-attach" {
  role       = module.ec2_role.iam_role_name
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWebTier"
}

resource "aws_iam_role_policy_attachment" "elastic-beanstalk-multicontainer-docker-attach" {
  role       = module.ec2_role.iam_role_name
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkMulticontainerDocker"
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = module.ec2_role.iam_role_name
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
  dns_name                  = module.app_compute_elastic_beanstalk.app_cname
  alb_zone_id               = module.app_compute_elastic_beanstalk.zone_id
  domain_validation_options = module.app_network_cert.domain_validation_options
}

module "app_database_serverless_postgres" {
  source = "../modules/database/rds/aurora/postgres"

  app_name                  = var.app_name
  app_env_name              = "${lower(var.app_name)}-${lower(var.environment)}"
  subnet_id_1               = module.app_network_vpc.vpc_subnet_private-a_id
  subnet_id_2               = module.app_network_vpc.vpc_subnet_private-c_id
  vpc_id                    = module.app_network_vpc.vpc_id
  security_group_id         = module.app_database_serverless_postgres.security_group_id
  identifier                = "mrspostgres"
  engine                    = "aurora-postgresql"
  engine_version            = "10.5"
  db_name                   = "appdb"
  username                  = var.db_postgres_username
  db_password               = random_password.password.result
  db_parameter_group_family = "postgres10"
}

module "app_compute_elastic_beanstalk" {
  source = "../modules/compute/elastic_beanstalk"

  app_name             = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_description      = "Elastic Beanstalk Application"
  service_role         = module.ec2_role.iam_role_arn
  solution_stack_name  = "64bit Amazon Linux 2 v3.2.5 running Corretto 11"
  app_env              = "blue"
  cname_prefix         = "app-mrs"
  ssh_key_name         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name
  instance_type        = "t2.small"
  vpc_id               = module.app_network_vpc.vpc_id
  subnet_id            = module.app_network_vpc.vpc_subnet_public-a_id
  environment          = var.environment
  environment_variables = {
    RDS_DB_NAME  = module.app_database_serverless_postgres.rds_dbname
    RDS_USERNAME = module.app_database_serverless_postgres.rds_username
    RDS_PASSWORD = module.app_database_serverless_postgres.rds_password
    RDS_HOSTNAME = module.app_database_serverless_postgres.rds_hostname
    RDS_PORT     = module.app_database_serverless_postgres.rds_port
    RDS_URL      = "jdbc:postgresql://${module.app_database_serverless_postgres.rds_hostname}:${module.app_database_serverless_postgres.rds_port}/${module.app_database_serverless_postgres.rds_dbname}"
  }
  environment_variable_keys = {
    RDS_DB_NAME  = "SPRING_FLYWAY_SCHEMAS"
    RDS_USERNAME = "SPRING_DATASOURCE_USERNAME"
    RDS_PASSWORD = "SPRING_DATASOURCE_PASSWORD"
    RDS_HOSTNAME = "PRD_RDS_HOSTNAME"
    RDS_PORT     = "PRD_RDS_PORT"
    RDS_URL      = "SPRING_DATASOURCE_URL"
  }
  acm_certificate_arn = module.app_network_cert.acm_certificate_arn
}

module "app_management_group" {
  source      = "../modules/management/resource_groups"
  group_name  = local.group_name
  environment = var.environment
}

module "app_management_ci" {
  source                       = "./ci"
  name                         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-pipeline"
  deploy_bucket_name           = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  full_repository_id           = "k2works/mrs"
  blanch_name                  = "develop"
  code_build_project_name      = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  code_deploy_application_name = module.app_compute_elastic_beanstalk.app_name
  code_deploy_environment_name = module.app_compute_elastic_beanstalk.app_env_name
}
