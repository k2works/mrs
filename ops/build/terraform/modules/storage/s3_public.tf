resource "aws_s3_bucket" "public" {
  bucket = var.public_bucket_name
  acl = "public-read"

  cors_rule {
    allowed_origins = ["https://${var.domain}"]
    allowed_methods = ["GET"]
    allowed_headers = ["*"]
    max_age_seconds = 3000
  }
}
