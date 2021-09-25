variable "domain" {}
variable "app_domain" {}
variable "app_runner_arn" {}

data "aws_route53_zone" "domain" {
  name = var.domain
}

resource "aws_apprunner_custom_domain_association" "app_domain" {
  domain_name = var.app_domain
  service_arn = var.app_runner_arn
}

resource "aws_route53_record" "app_domain" {
  zone_id = data.aws_route53_zone.domain.zone_id
  name    = aws_apprunner_custom_domain_association.app_domain.domain_name
  type    = "CNAME"
  ttl     = "300"
  records = [var.app_domain.dns_target]
}

resource "aws_route53_record" "certificate_validation" {
  for_each = {
  for record in aws_apprunner_custom_domain_association.app_domain.certificate_validation_records : record.name => {
    name   = record.name
    record = record.value
  }
  }

  zone_id = data.aws_route53_zone.domain.zone_id
  name    = each.value.name
  type    = "CNAME"
  ttl     = "300"
  records = [each.value.record]
}

