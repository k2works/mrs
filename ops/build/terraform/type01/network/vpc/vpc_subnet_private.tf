resource "aws_subnet" "private-a" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.2.0/24"
  availability_zone = var.az_1

  tags = {
    Name = "${var.app_name}-Private-a"
  }
}

resource "aws_subnet" "private-c" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.3.0/24"
  availability_zone = var.az_2

  tags = {
    Name = "${var.app_name}-Private-c"
  }
}

