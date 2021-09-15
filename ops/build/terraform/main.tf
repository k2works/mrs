provider "aws" {
  profile = "k2works"
  region  = "ap-northeast-1"
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

  required_version = ">= 0.14.9"

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

module "app_security_iam" {
  source = "./modules/security/iam"
}

module "app_security_kms" {
  source = "./modules/security/kms"

  org_name     = var.org_name
  app_name     = var.app_name
  ops_user_arn = module.app_security_iam.iam_user_1_arn
  dev_user_arn = module.app_security_iam.iam_user_2_arn
  ec2_role_arn = module.app_security_iam.iam_role_ec2_arn
}

module "app_security_tls" {
  source = "./modules/security/tls"

  tls_key_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
}

module "app_network" {
  source = "./modules/network/vpc"

  org_name = var.org_name
  vpc_name = var.vpc_name
  app_name = var.app_name
}

module "app_compute_security" {
  source = "./modules/compute/security_group"

  org_name = var.org_name
  vpc_name = var.vpc_name
  app_name = var.app_name
  vpc_id   = module.app_network.vpc_id
}

module "app_database_mysql" {
  source = "./modules/database/rds/mysql"

  app_name                  = var.app_name
  app_env_name              = "${lower(var.app_name)}-${lower(var.environment)}"
  subnet_id_1               = module.app_network.vpc_subnet_private-a_id
  subnet_id_2               = module.app_network.vpc_subnet_private-c_id
  vpc_id                    = module.app_network.vpc_id
  security_group_id         = module.app_database_mysql.security_group_id
  identifier                = "mrsmysql"
  instance_class            = "db.t2.small"
  allocated_storage         = "5"
  engine                    = "mysql"
  engine_version            = "5.7.16"
  db_name                   = "appdb"
  username                  = var.db_mysql_username
  db_password               = random_password.password.result
  db_parameter_group_family = "mysql5.7"
}

module "app_database_postgres" {
  source = "./modules/database/rds/postgres"

  app_name                  = var.app_name
  app_env_name              = "${lower(var.app_name)}-${lower(var.environment)}"
  subnet_id_1               = module.app_network.vpc_subnet_private-a_id
  subnet_id_2               = module.app_network.vpc_subnet_private-c_id
  vpc_id                    = module.app_network.vpc_id
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

module "app_compute_ec2" {
  source = "./modules/compute/ec2"

  app_name             = "${var.org_name}${var.vpc_name}${var.app_name}"
  ssh_key_name         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  vpc_id               = module.app_network.vpc_id
  ami_image            = var.images.custom
  security_group_id    = module.app_compute_security.app_security_group_id
  subnet_id            = module.app_network.vpc_subnet_public-a_id
  instance_name        = var.instance_name
  instance_type        = "t2.micro"
  instance_volume_type = "gp2"
  instance_volume_size = "10"
  public               = "true"
  environment          = var.environment
  iam_instance_profile = module.app_security_iam.iam_instance_profile_ec2
  security_group_ids = ""
}

module "app_compute_elastic_beanstalk" {
  source = "./modules/compute/elastic_beanstalk"

  app_name             = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_description      = "Elastic Beanstalk Application"
  service_role         = module.app_security_iam.iam_role_ec2_arn
  solution_stack_name  = "64bit Amazon Linux 2 v3.2.4 running Corretto 11"
  app_env              = "blue"
  cname_prefix         = "app-mrs"
  ssh_key_name         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  iam_instance_profile = module.app_security_iam.iam_instance_profile_ec2
  instance_type        = "t2.micro"
  vpc_id               = module.app_network.vpc_id
  subnet_id            = module.app_network.vpc_subnet_public-a_id
  environment          = var.environment
  environment_variables = {
    RDS_DB_NAME  = module.app_database_postgres.rds_dbname
    RDS_USERNAME = module.app_database_postgres.rds_username
    RDS_PASSWORD = module.app_database_postgres.rds_password
    RDS_HOSTNAME = module.app_database_postgres.rds_hostname
    RDS_PORT     = module.app_database_postgres.rds_port
    RDS_URL      = "jdbc:postgresql://${module.app_database_postgres.rds_hostname}:${module.app_database_postgres.rds_port}/${module.app_database_postgres.rds_dbname}"
  }
  environment_variable_keys = {
    RDS_DB_NAME  = "SPRING_FLYWAY_SCHEMAS"
    RDS_USERNAME = "SPRING_DATASOURCE_USERNAME"
    RDS_PASSWORD = "SPRING_DATASOURCE_PASSWORD"
    RDS_HOSTNAME = "PRD_RDS_HOSTNAME"
    RDS_PORT     = "PRD_RDS_PORT"
    RDS_URL      = "SPRING_DATASOURCE_URL"
  }
  acm_certificate_arn = ""
}

module "app_management_ssm_parameter" {
  source      = "./modules/management/ssm_parameter"
  environment = upper(var.environment)
  environment_variables = {
    SPRING_FLYWAY_SCHEMAS      = module.app_database_mysql.rds_dbname
    SPRING_DATASOURCE_USERNAME = module.app_database_mysql.rds_username
    SPRING_DATASOURCE_PASSWORD = module.app_database_mysql.rds_password
    SPRING_DATASOURCE_URL      = "jdbc:mysql://${module.app_database_mysql.rds_hostname}:${module.app_database_mysql.rds_port}/${module.app_database_mysql.rds_dbname}"
    RDS_HOSTNAME               = module.app_database_mysql.rds_hostname
    RDS_PORT                   = module.app_database_mysql.rds_port
  }
}

module "app_management_group" {
  source      = "./modules/management/resource_groups"
  group_name  = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  environment = var.environment
}

module "app_compute_s3" {
  source = "./modules/compute/s3"

  deploy_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  cert-bucket-name   = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-cert-bucket"
  tls_key_name       = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
}

module "app_management_codedeploy" {
  source = "./modules/management/codedeploy"

  app_name         = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_group_name   = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  service_role_arn = module.app_security_iam.iam_role_codedploy_arn
}

module "app_management_codebuild" {
  source = "./modules/management/codebuild"

  project_name        = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  project_description = "CodeBuildProject"
  vpc_id              = module.app_network.vpc_id
  public_subnet_arn   = module.app_network.vpc_subnet_public-a_arn
  public_subnet_id    = module.app_network.vpc_subnet_public-a_id
  private_subnet_arn  = module.app_network.vpc_subnet_private-c_arn
  private_subnet_id   = module.app_network.vpc_subnet_private-c_id
  security_group_id   = module.app_compute_security.app_security_group_id
  region              = "ap-northeast-1"
  deploy_bucket_name  = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  deploy_bucket_arn   = module.app_compute_s3.deploy_bucket_arn
  cert_bucket_arn     = module.app_compute_s3.cert_bucket_arn
  source_type         = "GITHUB"
  source_location     = "https://github.com/k2works/mrs.git"
  source_version      = "develop"
}

module "app_management_codepipeline" {
  source = "./modules/management/codepipeline"

  name                               = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-pipeline"
  deploy_bucket_name                 = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  full_repository_id                 = "k2works/mrs"
  blanch_name                        = "develop"
  code_build_project_name            = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  code_deploy_application_name       = "${var.org_name}${var.vpc_name}${var.app_name}"
  code_deploy_application_group_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
}

module "app_compute_apprunner" {
  source = "./modules/compute/apprunner"

  org_name        = lower(var.org_name)
  app_name        = lower(var.app_name)
  iam_policy_name = "apprunner_iam_policy"
  iam_role_name   = "apprunner_iam_role"
  environment_variables = {
    SPRING_PROFILES_ACTIVE     = "prod"
    SPRING_FLYWAY_SCHEMAS      = ""
    SPRING_DATASOURCE_USERNAME = "sa"
    SPRING_DATASOURCE_PASSWORD = "sa"
    SPRING_DATASOURCE_URL      = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
  }
}

module "app_mobile_amplify" {
  source ="./modules/mobile/amplify"
  app_api_url = "https://${module.app_compute_apprunner.service_url}/api"
  app_name = var.app_name
  app_repository = "https://github.com/k2works/mrs"
  access_token = var.github_personal_access_token
}

module "app_container" {
  source = "./modules/container"
  certificate_arn = var.acm_certificate_arn
  subnet_public_a_id = module.app_network.vpc_subnet_public-a_id
  subnet_public_c_id = module.app_network.vpc_subnet_public-c_id
  vpc_id = module.app_network.vpc_id
  subnet_private_a_id = module.app_network.vpc_subnet_private-a_id
  subnet_private_c_id = module.app_network.vpc_subnet_private-c_id
  cidr_block = module.app_network.vpc_cidr
  environment_variables = {
    SPRING_PROFILES_ACTIVE     = "prod"
    SPRING_FLYWAY_SCHEMAS      = ""
    SPRING_DATASOURCE_USERNAME = "sa"
    SPRING_DATASOURCE_PASSWORD = "sa"
    SPRING_DATASOURCE_URL      = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
  }
}
