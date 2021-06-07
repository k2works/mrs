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

module "app_security" {
  source = "./modules/security/iam"
}
