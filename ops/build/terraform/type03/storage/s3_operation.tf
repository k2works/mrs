resource "aws_s3_bucket" "operation" {
  bucket = var.operation_bucket_name

  lifecycle_rule {
    enabled = true

    expiration {
      days = "180"
    }
  }
}
