variable "org_name" {}
variable "vpc_name" {}
variable "app_name" {}

resource "aws_vpc" "app_vpc" {
  cidr_block = "10.0.0.0/16"
  instance_tenancy = "default"
  enable_dns_support = "true"
  enable_dns_hostnames = "false"

  tags = {
    Name = "${var.org_name}-${var.vpc_name}-${var.app_name}"
  }
}

resource "aws_internet_gateway" "app_gw" {
  vpc_id = aws_vpc.app_vpc.id
}

resource "aws_subnet" "public-a" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.0.0/24"
  availability_zone = "ap-northeast-1a"

  tags = {
    Name = "${var.app_name}-Public"
  }
}

resource "aws_route_table" "public-route" {
  vpc_id = aws_vpc.app_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.app_gw.id
  }
}

resource "aws_route_table_association" "public-a" {
  subnet_id = aws_subnet.public-a.id
  route_table_id = aws_route_table.public-route.id
}

resource "aws_subnet" "private-c" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.1.0/24"
  availability_zone = "ap-northeast-1c"

  tags = {
    Name = "${var.app_name}-Private"
  }
}

output "vpc_id" {
  value = aws_vpc.app_vpc.id
}
output "vpc_cidr" {
  value = aws_vpc.app_vpc.cidr_block
}
output "vpc_subnet_public-a_id" {
  value = aws_subnet.public-a.id
}
output "vpc_subnet_private-c_id" {
  value = aws_subnet.private-c.id
}
