resource "aws_lb_target_group" "default" {
  name = var.name
  target_type = "ip"
  vpc_id = var.vpc_id
  port = 80
  protocol = "HTTP"
  deregistration_delay = 300

  health_check {
    path = "/"
    healthy_threshold = 5
    unhealthy_threshold = 2
    timeout = 5
    interval = 30
    matcher = 200
    port = "traffic-port"
    protocol = "HTTP"
  }

  depends_on = [aws_lb.default]
}

resource "aws_lb_target_group_attachment" "default" {
  target_group_arn = aws_lb_target_group.default.arn
  target_id = var.target_id
}
