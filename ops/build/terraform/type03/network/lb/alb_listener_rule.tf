resource "aws_lb_listener_rule" "default" {
  listener_arn = aws_lb_listener.https.arn
  priority = 100

  action {
    type = "forward"
    target_group_arn = aws_lb_target_group.default.arn
  }

  condition {
    path_pattern {
      values = ["/*"]
    }
  }
}
