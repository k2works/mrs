variable "environment" {
  type    = string
  default = "Production"
}

variable "provider_config" {
  type = map(any)
  default = {
    profile = "k2works"
    regions = "ap-northeast-1"
  }
}

variable "org_name" {
  type    = string
  default = "MrsOrg"
}

variable "app_name" {
  type    = string
  default = "MrsApplication"
}

variable "vpc_name" {
  type    = string
  default = "MrsNetwork"
}

variable "domain" {
  type    = string
  default = "k2works-lab.cf"
}

variable "images" {
  default = {
    us-east-1      = "ami-1ecae776"
    us-west-2      = "ami-e7527ed7"
    us-west-1      = "ami-d114f295"
    eu-west-1      = "ami-a10897d6"
    eu-central-1   = "ami-a8221fb5"
    ap-southeast-1 = "ami-68d8e93a"
    ap-southeast-2 = "ami-fd9cecc7"
    ap-northeast-1 = "ami-0ca38c7440de1749a"
    sa-east-1      = "ami-b52890a8"
    custom         = "ami-0661c819b0a4bb2b2"
  }
}

variable "instance_name" {
  type    = string
  default = "MrsProduction"
}

variable "db_mysql_username" {}
variable "github_personal_access_token" {}
variable "ecr_url" {}
variable "dockerhub_user" {}
variable "dockerhub_pass" {}

