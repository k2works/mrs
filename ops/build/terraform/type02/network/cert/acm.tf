variable "domain" {}
variable "sub_domain" {}

resource "aws_acm_certificate" "app_domain" {
  domain_name = "${var.sub_domain}.${var.domain}"
  subject_alternative_names = []
  validation_method = "DNS"

  lifecycle {
    create_before_destroy = true
  }
}

output "acm_certificate_arn" {
  value = aws_acm_certificate.app_domain.arn
}

output "domain_validation_options" {
  value = aws_acm_certificate.app_domain.domain_validation_options
}

