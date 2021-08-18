variable "org_name" {
  description = "Organization Name"
  type = string
  default = "MrsOrg"
}

variable "s3_bucket_name" {
  description = "Application Name"
  type = string
  default = "mrs-org-mrs-production-tfstate"
}

variable "app_name" {
  description = "Application Name"
  type = string
  default = "MrsProduction"
}

variable "vpc_name" {
  description = "Virtual Private Cloud Name"
  type = string
  default = "VPC"
}

variable "instance_name" {
  description = "Value of the Name tag for the EC2 instance"
  type = string
  default = "MrsProduction"
}

variable "images" {
  default = {
    us-east-1 = "ami-1ecae776"
    us-west-2 = "ami-e7527ed7"
    us-west-1 = "ami-d114f295"
    eu-west-1 = "ami-a10897d6"
    eu-central-1 = "ami-a8221fb5"
    ap-southeast-1 = "ami-68d8e93a"
    ap-southeast-2 = "ami-fd9cecc7"
    ap-northeast-1 = "ami-0ca38c7440de1749a"
    sa-east-1 = "ami-b52890a8"
    custom = "ami-0661c819b0a4bb2b2"
  }
}

variable "environment" {
  description = "Application environment"
  type = string
  default = "Production"
}

variable "db_mysql_username" {}
variable "db_postgres_username" {}
