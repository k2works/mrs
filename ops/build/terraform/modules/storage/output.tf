output "alb_log" {
  value = aws_s3_bucket.alb_log
}

output "deploy_bucket_arn" {
  value = aws_s3_bucket.deploy_bucket.arn
}

output "cert_bucket_arn" {
  value = aws_s3_bucket.cert-bucket.arn
}
