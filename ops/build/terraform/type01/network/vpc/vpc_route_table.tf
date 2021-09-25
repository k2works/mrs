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

resource "aws_route_table" "private-a" {
  vpc_id = aws_vpc.app_vpc.id

  tags = {
    Name = "example-rt-pri-a"
  }
}
resource "aws_route_table" "private-c" {
  vpc_id = aws_vpc.app_vpc.id

  tags = {
    Name = "example-rt-pri-c"
  }
}
resource "aws_route_table_association" "private_a" {
  route_table_id = aws_route_table.private-a.id
  subnet_id      = aws_subnet.private-a.id
}
resource "aws_route_table_association" "private_c" {
  route_table_id = aws_route_table.private-c.id
  subnet_id      = aws_subnet.private-c.id
}
