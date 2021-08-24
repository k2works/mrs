resource "aws_lb" "example" {
  name                       = "example"
  load_balancer_type         = "application"
  internal                   = false
  idle_timeout               = 60
  enable_deletion_protection = false
  subnets = [
    var.subnet_public_a_id,
    var.subnet_public_c_id,
  ]
  access_logs {
    bucket  = aws_s3_bucket.alb_log.id
    prefix  = "example-alb"
    enabled = true
  }
  security_groups = [
    module.http_sg.security_group_id,
    module.https_sg.security_group_id,
    module.rds_mysql_sg.security_group_id,
  ]
}
output "alb_dns_name" {
  value = aws_lb.example.dns_name
}

resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.example.arn
  port     = "80"
  protocol = "HTTP"
  default_action {
    type = "redirect"
    redirect {
      port        = "443"
      protocol    = "HTTPS"
      status_code = "HTTP_301"
    }
  }
}
resource "aws_lb_listener" "https" {
  load_balancer_arn = aws_lb.example.arn
  port              = "443"
  protocol          = "HTTPS"
  certificate_arn   = var.certificate_arn
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  default_action {
    type = "fixed-response"
    fixed_response {
      content_type = "text/plain"
      message_body = "これは「HTTPS」です"
      status_code  = "200"
    }
  }
}

resource "aws_lb_target_group" "ecs" {
  name                 = "example"
  target_type          = "ip"
  vpc_id               = var.vpc_id
  port                 = 80
  protocol             = "HTTP"
  deregistration_delay = 300
  health_check {
    path                = "/"
    healthy_threshold   = 5
    unhealthy_threshold = 2
    timeout             = 5
    interval            = 30
    matcher             = 200
    port                = "traffic-port"
    protocol            = "HTTP"
  }
  depends_on = [aws_lb.example]
}

resource "aws_lb_listener_rule" "ecs" {
  listener_arn = aws_lb_listener.https.arn
  priority     = 100
  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ecs.arn
  }
  condition {
    path_pattern {
      values = ["/*"]
    }
  }
}
