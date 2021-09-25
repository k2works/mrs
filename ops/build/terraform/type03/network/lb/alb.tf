resource "aws_lb" "default" {
  name                       = var.name
  load_balancer_type         = "application"
  internal                   = false
  idle_timeout               = 60
  enable_deletion_protection = false

  subnets = [
    var.subnet_public_a.id,
    var.subnet_public_c.id,
  ]

  access_logs {
    bucket  = var.alb_log_bucket.id
    enabled = true
  }

  security_groups = [
    var.http_sg.security_group_id,
    var.https_sg.security_group_id,
    var.http_redirect_sg.security_group_id,
  ]
}

