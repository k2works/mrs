resource "aws_instance" "app_server" {
  ami = var.ami_image
  instance_type = var.instance_type
  key_name = var.ssh_key_name
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

