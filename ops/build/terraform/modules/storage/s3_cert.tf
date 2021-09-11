# Create a bucket
resource "aws_s3_bucket" "cert-bucket" {

  bucket = var.cert_bucket_name

  acl    = "private"   # or can be "public-read"
}

# Upload an object
resource "aws_s3_bucket_object" "key" {

  bucket = aws_s3_bucket.cert-bucket.id

  key    = "${var.tls_key_name}.key"

  acl    = "private"  # or can be "public-read"

  source = "../../../config/tls/${var.tls_key_name}.key"

  etag = filemd5("../../../config/tls/${var.tls_key_name}.key")

}

resource "aws_s3_bucket_object" "cert" {

  bucket = aws_s3_bucket.cert-bucket.id

  key    = "${var.tls_key_name}.crt"

  acl    = "private"  # or can be "public-read"

  source = "../../../config/tls/${var.tls_key_name}.crt"

  etag = filemd5("../../../config/tls/${var.tls_key_name}.crt")

}

