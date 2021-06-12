resource "aws_elastic_beanstalk_environment" "app_production" {
  name = "${var.app_name}-${var.app_env}"
  application = aws_elastic_beanstalk_application.app.name
  solution_stack_name = var.solution_stack_name

  cname_prefix = "${var.cname_prefix}-${var.app_env}"

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name = "EC2KeyName"
    value = var.ssh_key_name
  }

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name = "IamInstanceProfile"
    value = var.iam_instance_profile
  }

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name = "InstanceType"
    value = var.instance_type
  }

  setting {
    namespace = "aws:ec2:vpc"
    name = "VPCId"
    value = var.vpc_id
  }

  setting {
    namespace = "aws:ec2:vpc"
    name = "Subnets"
    value = var.subnet_id
  }

  setting {
    namespace = "aws:ec2:vpc"
    name = "AssociatePublicIpAddress"
    value = true
  }
}
