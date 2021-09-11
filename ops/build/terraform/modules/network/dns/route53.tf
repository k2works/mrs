data "aws_route53_zone" "domain" {
  name = var.domain
}

resource "aws_route53_record" "app_domain" {
  zone_id = data.aws_route53_zone.domain.zone_id
  name = "${var.sub_domain}.${data.aws_route53_zone.domain.name}"
  type = "A"

  alias {
    name = var.dns_name
    zone_id = var.alb_zone_id
    evaluate_target_health = true
  }
}

resource "aws_route53_record" "domain_certificate" {
  for_each = {
    for dvo in aws_acm_certificate.app_domain.domain_validation_options : dvo.domain_name => {
        name = dvo.resource_record_name
        record = dvo.resource_record_value
        type = dvo.resource_record_type
    }
  }

  allow_overwrite = true
  name = each.value.name
  type = each.value.type
  records = [each.value.record]
  ttl = 60
  zone_id = data.aws_route53_zone.domain.id
}
