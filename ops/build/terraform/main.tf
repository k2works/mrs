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

  ops_user_arn = module.app_security_iam.iam_user_1_arn
  dev_user_arn = module.app_security_iam.iam_user_2_arn
  ec2_role_arn = module.app_security_iam.iam_role_ec2_arn
}
