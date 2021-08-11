variable "org_name" {}
variable "vpc_name" {}
variable "app_name" {}
variable "vpc_id" {}

resource "aws_security_group" "app_security" {
  name = "${var.org_name}-${var.vpc_name}-${var.app_name}"
  description = "Allow SSH inbound traffic"
  vpc_id = var.vpc_id
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }
  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }
  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"]
  }

  tags = {
    Name = "${var.org_name}-${var.vpc_name}-${var.app_name}"
  }
}

resource "aws_security_group" "db_security" {
  name   = "mss_rds"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "app_security_group_id" {
  value = aws_security_group.app_security.id
}

output "db_security_group_id" {
  value = aws_security_group.db_security.id
}

