provider "aws" {
  profile = "k2works"
  region = "ap-northeast-1"
  default_tags {
    tags = {
      Environment = var.environment
    }
  }
}

terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 0.14.9"

  backend "s3" {
    bucket = "mrs-org-mrs-production-tfstate"
    region = "ap-northeast-1"
    profile = "k2works"
    key = "terraform.tfstate"
    encrypt = true
  }
}

module "app_security_iam" {
  source = "./modules/security/iam"
}

module "app_security_kms" {
  source = "./modules/security/kms"

  org_name = var.org_name
  app_name = var.app_name
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
  vpc_id = module.app_network.vpc_id
}

module "app_compute_ec2" {
  source = "./modules/compute/ec2"

  app_name = "${var.org_name}${var.vpc_name}${var.app_name}"
  ssh_key_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  vpc_id = module.app_network.vpc_id
  ami_image = var.images.custom
  security_group_id = module.app_compute_security.security_group_id
  subnet_id = module.app_network.vpc_subnet_public-a_id
  instance_name = var.instance_name
  instance_type = "t2.micro"
  instance_volume_type = "gp2"
  instance_volume_size = "10"
  public = "true"
  environment = var.environment
  iam_instance_profile = module.app_security_iam.iam_instance_profile_ec2
}

module "app_compute_elastic_beanstalk" {
  source = "./modules/compute/elastic_beanstalk"

  app_name = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_description = "Elastic Beanstalk Application"
  service_role = module.app_security_iam.iam_role_ec2_arn
  solution_stack_name = "64bit Amazon Linux 2 v3.2.1 running Corretto 11"
  app_env = "blue"
  cname_prefix = "app-mrs"
  ssh_key_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  iam_instance_profile = module.app_security_iam.iam_instance_profile_ec2
  instance_type = "t2.micro"
  vpc_id = module.app_network.vpc_id
  subnet_id = module.app_network.vpc_subnet_public-a_id
  environment = var.environment
}

module "app_management_group" {
  source = "./modules/management/resource_groups"
  group_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  environment = var.environment
}

module "app_compute_s3" {
  source = "./modules/compute/s3"

  deploy_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  cert-bucket-name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-cert-bucket"
  tls_key_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}"
}

module "app_management_codedeploy" {
  source = "./modules/management/codedeploy"

  app_name = "${var.org_name}${var.vpc_name}${var.app_name}"
  app_group_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
  service_role_arn = module.app_security_iam.iam_role_codedploy_arn
}

module "app_management_codebuild" {
  source = "./modules/management/codebuild"

  project_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  project_description = "CodeBuildProject"
  vpc_id = module.app_network.vpc_id
  public_subnet_arn = module.app_network.vpc_subnet_public-a_arn
  public_subnet_id = module.app_network.vpc_subnet_public-a_id
  private_subnet_arn = module.app_network.vpc_subnet_private-c_arn
  private_subnet_id = module.app_network.vpc_subnet_private-c_id
  security_group_id = module.app_compute_security.security_group_id
  region = "ap-northeast-1"
  deploy_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  deploy_bucket_arn = module.app_compute_s3.deploy_bucket_arn
  cert_bucket_arn = module.app_compute_s3.cert_bucket_arn
  source_type = "GITHUB"
  source_location = "https://github.com/k2works/mrs.git"
  source_version = "develop"
}

module "app_management_codepipeline" {
  source = "./modules/management/codepipeline"

  name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-pipeline"
  deploy_bucket_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-deploy-bucket"
  full_repository_id = "k2works/mrs"
  blanch_name = "develop"
  code_build_project_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-project"
  code_deploy_application_name = "${var.org_name}${var.vpc_name}${var.app_name}"
  code_deploy_application_group_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-group"
}
