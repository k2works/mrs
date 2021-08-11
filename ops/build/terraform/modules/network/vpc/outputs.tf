output "vpc_id" {
  value = aws_vpc.app_vpc.id
}
output "vpc_cidr" {
  value = aws_vpc.app_vpc.cidr_block
}
output "vpc_subnet_public-a_arn" {
  value = aws_subnet.public-a.arn
}
output "vpc_subnet_public-a_id" {
  value = aws_subnet.public-a.id
}
output "vpc_subnet_public-c_arn" {
  value = aws_subnet.public-c.arn
}
output "vpc_subnet_public-c_id" {
  value = aws_subnet.public-c.id
}
output "vpc_subnet_private-a_arn" {
  value = aws_subnet.private-a.arn
}
output "vpc_subnet_private-a_id" {
  value = aws_subnet.private-a.id
}
output "vpc_subnet_private-c_arn" {
  value = aws_subnet.private-c.arn
}
output "vpc_subnet_private-c_id" {
  value = aws_subnet.private-c.id
}
