variable "vpc_id" {}
variable "ami_image" {}
variable "security_group_id" {}
variable "subnet_id" {}
variable "instance_name" {}
variable "instance_type" {}
variable "instance_key_name" {}
variable "instance_volume_type" {}
variable "instance_volume_size" {}
variable "public" {}
variable "environment" {}
variable "iam_instance_profile" {}

resource "aws_instance" "app_server" {
  ami = var.ami_image
  instance_type = var.instance_type
  key_name = var.instance_key_name
  vpc_security_group_ids = [
    var.security_group_id
  ]
  subnet_id = var.subnet_id
  associate_public_ip_address = var.public
  root_block_device {
    volume_type = var.instance_volume_type
    volume_size = var.instance_volume_size
  }
  iam_instance_profile = var.iam_instance_profile

  tags = {
    Name = "${var.instance_name}-${var.environment}"
  }
}

output "instance_id" {
  value = aws_instance.app_server.id
}

output "public_ip" {
  value = aws_instance.app_server.public_ip
}

output "private_ip" {
  value = aws_instance.app_server.private_ip
}
