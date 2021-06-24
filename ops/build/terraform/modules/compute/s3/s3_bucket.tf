variable "deploy_bucket_name" {}

resource "aws_s3_bucket" "deploy_bucket" {
  bucket = var.deploy_bucket_name
  acl = "private"
  tags = {
    Name = var.deploy_bucket_name
  }
  versioning {
    enabled = true
  }
}

output "deploy_bucket_arn" {
  value = aws_s3_bucket.deploy_bucket.arn
}
