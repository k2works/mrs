
resource "aws_s3_bucket" "deploy_bucket" {
  bucket = var.deploy_bucket_name
  acl = "private"
  versioning {
    enabled = true
  }
}

