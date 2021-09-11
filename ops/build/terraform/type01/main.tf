locals {
  name               = "${var.org_name}${var.vpc_name}${var.app_name}"
  bucket_name        = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
  lb_name            = lower(var.app_name)
  subdomain_name     = lower(var.app_name)
  group_name         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  deploy_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  cert_bucket_name   = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-cert-bucket"
  tls_key_name       = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
  api_url            = "https://${local.subdomain_name}.${var.domain}/api"
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

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = module.ec2_role.iam_role_name
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
  source              = "../modules/storage"
  log_bucket_name     = "${local.bucket_name}-alb-log"
  private_bucket_name = "${local.bucket_name}-private"
  public_bucket_name  = "${local.bucket_name}-public"
  domain              = var.domain
  cert_bucket_name    = local.cert_bucket_name
  deploy_bucket_name  = local.deploy_bucket_name
  tls_key_name        = local.tls_key_name
}

module "app_network_vpc" {
  source   = "../modules/network/vpc"
  org_name = var.org_name
  vpc_name = var.vpc_name
  app_name = var.app_name
}

module "app_network_dns" {
  source      = "../modules/network/dns"
  domain      = var.domain
  sub_domain  = local.subdomain_name
  dns_name    = module.app_network_loadbalancer.alb_dns_name
  alb_zone_id = module.app_network_loadbalancer.alb_zone_id
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
  acm_certificate_arn = module.app_network_dns.acm_certificate_arn
  vpc_id              = module.app_network_vpc.vpc_id
  target_id           = module.app_compute_ec2.private_ip
}

module "app_compute_ec2" {
  source               = "../modules/compute/ec2"
  app_name             = "${var.org_name}${var.vpc_name}${var.app_name}"
  ssh_key_name         = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  vpc_id               = module.app_network_vpc.vpc_id
  ami_image            = var.images.custom
  security_group_ids   = [module.app_security_group_http.security_group_id, module.app_security_group_https.security_group_id, module.app_security_group_ssh.security_group_id]
  subnet_id            = module.app_network_vpc.vpc_subnet_public-a_id
  instance_name        = var.instance_name
  instance_type        = "t2.micro"
  instance_volume_type = "gp2"
  instance_volume_size = "10"
  public               = "true"
  environment          = var.environment
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name
  security_group_id    = ""
}

module "app_database_mysql" {
  source                    = "../modules/database/rds/mysql"
  app_name                  = var.app_name
  app_env_name              = "${lower(var.app_name)}-${lower(var.environment)}"
  subnet_id_1               = module.app_network_vpc.vpc_subnet_private-a_id
  subnet_id_2               = module.app_network_vpc.vpc_subnet_private-c_id
  vpc_id                    = module.app_network_vpc.vpc_id
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

module "app_management_ssm_parameter" {
  source      = "../modules/management/ssm_parameter"
  environment = upper(var.environment)
  environment_variables = {
    SPRING_FLYWAY_SCHEMAS      = module.app_database_mysql.rds_dbname
    SPRING_DATASOURCE_USERNAME = module.app_database_mysql.rds_username
    SPRING_DATASOURCE_PASSWORD = module.app_database_mysql.rds_password
    SPRING_DATASOURCE_URL      = "jdbc:mysql://${module.app_database_mysql.rds_hostname}:${module.app_database_mysql.rds_port}/${module.app_database_mysql.rds_dbname}"
    RDS_HOSTNAME               = module.app_database_mysql.rds_hostname
    RDS_PORT                   = module.app_database_mysql.rds_port
    API_URL                    = local.api_url
  }
}

module "app_management_group" {
  source      = "../modules/management/resource_groups"
  group_name  = local.group_name
  environment = var.environment
}

module "app_management_codedeploy" {
  source           = "../modules/management/codedeploy"
  app_name         = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_group_name   = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  service_role_arn = module.codedeploy_role.iam_role_arn
}

module "app_management_codebuild" {
  source              = "../modules/management/codebuild"
  project_name        = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  project_description = "CodeBuildProject"
  vpc_id              = module.app_network_vpc.vpc_id
  public_subnet_arn   = module.app_network_vpc.vpc_subnet_public-a_arn
  public_subnet_id    = module.app_network_vpc.vpc_subnet_public-a_id
  private_subnet_arn  = module.app_network_vpc.vpc_subnet_private-c_arn
  private_subnet_id   = module.app_network_vpc.vpc_subnet_private-c_id
  region              = "ap-northeast-1"
  deploy_bucket_name  = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  deploy_bucket_arn   = module.app_storage.deploy_bucket_arn
  cert_bucket_arn     = module.app_storage.cert_bucket_arn
  source_type         = "GITHUB"
  source_location     = "https://github.com/k2works/mrs.git"
  source_version      = "develop"
}

module "app_management_codepipeline" {
  source                             = "../modules/management/codepipeline"
  name                               = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-pipeline"
  deploy_bucket_name                 = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  full_repository_id                 = "k2works/mrs"
  blanch_name                        = "develop"
  code_build_project_name            = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  code_deploy_application_name       = "${var.org_name}${var.vpc_name}${var.app_name}"
  code_deploy_application_group_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
}
