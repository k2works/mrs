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
