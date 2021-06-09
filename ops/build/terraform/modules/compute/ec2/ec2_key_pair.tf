variable "ssh_key_name" {}

resource "tls_private_key" "this" {
  algorithm = "RSA"
}

resource "aws_key_pair" "ssh_key" {
  key_name = var.ssh_key_name
  public_key = tls_private_key.this.public_key_openssh

  provisioner "local-exec" {
    command = "echo '${tls_private_key.this.private_key_pem}' > ./${var.ssh_key_name}.pem"
  }
  provisioner "local-exec" {
    command = "mv  ./${var.ssh_key_name}.pem ../../config/ssh/${var.ssh_key_name}.pem"
  }
  provisioner "local-exec" {
    command = "chmod 600 ../../config/ssh/${var.ssh_key_name}.pem"
  }

  provisioner "local-exec" {
    command = "echo '${tls_private_key.this.public_key_pem}' > ./${var.ssh_key_name}.pub.pem"
  }
  provisioner "local-exec" {
    command = "mv  ./${var.ssh_key_name}.pub.pem ../../config/ssh/${var.ssh_key_name}.pub.pem"
  }
  provisioner "local-exec" {
    command = "chmod 600 ../../config/ssh/${var.ssh_key_name}.pub.pem"
  }
}

output "ssh_key_name" {
  value = var.ssh_key_name
}
