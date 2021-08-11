resource "aws_subnet" "public-a" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.0.0/24"
  availability_zone = var.az_1

  tags = {
    Name = "${var.app_name}-Public-a"
  }
}

resource "aws_subnet" "public-c" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.1.0/24"
  availability_zone = var.az_1

  tags = {
    Name = "${var.app_name}-Public-c"
  }
}

