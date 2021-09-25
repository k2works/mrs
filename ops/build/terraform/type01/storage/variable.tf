variable "private_bucket_name" {}
variable "public_bucket_name" {}
variable "log_bucket_name" {}
variable "domain" {}
data "aws_caller_identity" "iam" {}
variable "cert_bucket_name" {}
variable "tls_key_name" {}
variable "deploy_bucket_name" {}
