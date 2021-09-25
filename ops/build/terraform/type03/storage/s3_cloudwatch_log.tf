resource "aws_s3_bucket" "cloudwatch_logs" {
  bucket = var.cloudwatch_logs_bucket_name

  lifecycle_rule {
    enabled = true

    expiration {
      days = "180"
    }
  }
}
