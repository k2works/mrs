resource "aws_acm_certificate" "app_domain" {
  domain_name = "${var.sub_domain}.${var.domain}"
  subject_alternative_names = []
  validation_method = "DNS"

  lifecycle {
    create_before_destroy = true
  }
}

