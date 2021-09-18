resource "aws_s3_bucket" "app_artifact" {
  bucket = var.artifact_bucket_name

  lifecycle_rule {
    enabled = true

    expiration {
      days = "180"
    }
  }
}
