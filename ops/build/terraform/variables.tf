variable "org_name" {
  description = "Organization Name"
  type = string
  default = "MrsOrg"
}

variable "app_name" {
  description = "Application Name"
  type = string
  default = "MrsProduction"
}

variable "s3_bucket_name" {
  description = "Application Name"
  type = string
  default = "mrs-org-mrs-production-tfstate"
}
