provider "aws" {
  profile = "k2works"
  region = "ap-northeast-1"
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

  ssh_key_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  vpc_id = module.app_network.vpc_id
  ami_image = var.images.ap-northeast-1
  security_group_id = module.app_compute_security.security_group_id
  subnet_id = module.app_network.vpc_subnet_public-a_id
  instance_name = var.instance_name
  instance_key_name = "${lower(var.org_name)}-${lower(var.vpc_name)}-${lower(var.app_name)}-key"
  instance_type = "t2.micro"
  instance_volume_type = "gp2"
  instance_volume_size = "10"
  public = "true"
  environment = "production"
  iam_instance_profile = module.app_security_iam.iam_instance_profile_ec2
}
