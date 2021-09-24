variable "kinesis_name" {}
variable "kinesis_role_arn" {}
variable "kinesis_bucket_arn" {}
variable "kinesis_prefix" {}

resource "aws_kinesis_firehose_delivery_stream" "app_stream" {
  name        = var.kinesis_name
  destination = "s3"

  s3_configuration {
    role_arn   = var.kinesis_role_arn
    bucket_arn = var.kinesis_bucket_arn
    prefix     = var.kinesis_prefix
  }
}

