resource "aws_vpc" "app_vpc" {
  cidr_block = "10.0.0.0/16"
  instance_tenancy = "default"
  enable_dns_support = "true"
  enable_dns_hostnames = "false"

  tags = {
    Name = "${var.org_name}-${var.vpc_name}-${var.app_name}"
  }
}

