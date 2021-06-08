resource "aws_subnet" "private-c" {
  vpc_id = aws_vpc.app_vpc.id
  cidr_block = "10.0.1.0/24"
  availability_zone = "ap-northeast-1c"

  tags = {
    Name = "${var.app_name}-Private"
  }
}

