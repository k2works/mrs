variable "cloudwatch_log_name" {}
variable "cloudwatch_log_log_group_name" {}
variable "cloudwatch_log_role_arn" {}
resource "aws_cloudwatch_log_subscription_filter" "app_log_filter" {
  name            = var.cloudwatch_log_name
  log_group_name  = var.cloudwatch_log_log_group_name
  destination_arn = aws_kinesis_firehose_delivery_stream.app_stream.arn
  filter_pattern = "[]"
  role_arn  = var.cloudwatch_log_role_arn
}

