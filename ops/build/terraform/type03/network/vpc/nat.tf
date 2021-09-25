resource "aws_eip" "nat_a" {
  vpc        = true
  depends_on = [aws_internet_gateway.app_gw]

  tags = {
    Name = "example-eip-a"
  }
}
resource "aws_eip" "nat_c" {
  vpc        = true
  depends_on = [aws_internet_gateway.app_gw]

  tags = {
    Name = "example-eip-c"
  }
}

resource "aws_nat_gateway" "nat_a" {
  allocation_id = aws_eip.nat_a.id
  subnet_id     = aws_subnet.public-a.id
  depends_on    = [aws_internet_gateway.app_gw]

  tags = {
    Name = "example-nat-gw-a"
  }
}
resource "aws_nat_gateway" "nat_c" {
  allocation_id = aws_eip.nat_c.id
  subnet_id     = aws_subnet.public-c.id
  depends_on    = [aws_internet_gateway.app_gw]

  tags = {
    Name = "example-nat-gw-c"
  }
}

resource "aws_route" "private_a" {
  route_table_id         = aws_route_table.private-a.id
  nat_gateway_id         = aws_nat_gateway.nat_a.id
  destination_cidr_block = "0.0.0.0/0"
}
resource "aws_route" "private_c" {
  route_table_id         = aws_route_table.private-c.id
  nat_gateway_id         = aws_nat_gateway.nat_c.id
  destination_cidr_block = "0.0.0.0/0"
}

