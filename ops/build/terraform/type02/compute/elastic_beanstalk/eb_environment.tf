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

  setting {
    namespace = "aws:elb:listener:443"
    name = "ListenerProtocol"
    value = "HTTPS"
  }

  setting {
    namespace = "aws:elb:listener:443"
    name = "InstanceProtocol"
    value = "HTTP"
  }

  setting {
    namespace = "aws:elb:listener:443"
    name = "InstancePort"
    value = "80"
  }

  setting {
    namespace = "aws:elb:listener:443"
    name = "SSLCertificateId"
    value = var.acm_certificate_arn
  }

  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name = var.environment_variable_keys.RDS_DB_NAME
    value = var.environment_variables.RDS_DB_NAME
  }

  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name = var.environment_variable_keys.RDS_USERNAME
    value = var.environment_variables.RDS_USERNAME
  }

  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name = var.environment_variable_keys.RDS_PASSWORD
    value = var.environment_variables.RDS_PASSWORD
  }

  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name = var.environment_variable_keys.RDS_HOSTNAME
    value = var.environment_variables.RDS_HOSTNAME
  }

  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name = var.environment_variable_keys.RDS_PORT
    value = var.environment_variables.RDS_PORT
  }

  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name = var.environment_variable_keys.RDS_URL
    value = var.environment_variables.RDS_URL
  }
}
