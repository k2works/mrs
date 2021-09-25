output "alb_dns_name" {
  value = aws_lb.default.dns_name
}

output "alb_zone_id" {
  value = aws_lb.default.zone_id
}

output "alb_target_group" {
  value = aws_lb_target_group.default
}
