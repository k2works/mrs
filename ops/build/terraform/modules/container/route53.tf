data "aws_route53_zone" "main" {
  name = "k2works-lab.cf"
}

resource "aws_route53_record" "example" {
  zone_id = data.aws_route53_zone.main.zone_id
  name    = data.aws_route53_zone.main.name
  type    = "A"
  alias {
    name                   = aws_lb.example.dns_name
    zone_id                = aws_lb.example.zone_id
    evaluate_target_health = true
  }
}
