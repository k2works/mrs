variable "profile" {
  type = string
  default = "k2works"
}
variable "region" {
  type = string
  default = "ap-northeast-1"
}

locals {
  timestamp = regex_replace(timestamp(), "[- TZ:]", "")
}


# source blocks are generated from your builders; a source can be referenced in
# build blocks. A build block runs provisioners and post-processors on a
# source.
source "amazon-ebs" "application-server" {
  profile = var.profile
  ami_name = "amazon-linux-2-${local.timestamp}"
  instance_type = "t2.micro"
  region = var.region
  source_ami_filter {
    filters = {
      name = "amzn2-ami-hvm-*-x86_64-gp2"
      root-device-type = "ebs"
      virtualization-type = "hvm"
    }
    most_recent = true
    owners = [
      "137112412989"]
  }
  ssh_username = "ec2-user"
  ssh_timeout = "5m"
}

# a build block invokes sources and runs provisioning steps on them.
build {
  sources = [
    "source.amazon-ebs.application-server"]
  provisioner "shell" {
    inline = [
      "sudo yum -y update",
      "sudo amazon-linux-extras install epel -y",
      "sudo yum -y install ansible"
    ]
  }

  provisioner "ansible-local" {
    playbook_file = "../scripts/playbook.yml"
  }
}
