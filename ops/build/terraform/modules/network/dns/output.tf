output "acm_certificate_arn" {
  value = aws_acm_certificate.app_domain.arn
}

output "domain_name" {
  value = aws_route53_record.app_domain.name
}
