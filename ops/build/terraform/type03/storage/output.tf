output "alb_log" {
  value = aws_s3_bucket.alb_log
}

output "deploy_bucket_arn" {
  value = aws_s3_bucket.deploy_bucket.arn
}

output "operation_bucket_id" {
  value = aws_s3_bucket.operation.id
}

output "cloudwatch_logs_bucket" {
  value = aws_s3_bucket.cloudwatch_logs
}
