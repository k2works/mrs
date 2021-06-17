variable "service_role_arn" {}

resource "aws_codedeploy_app" "web-provisioning" {
  name = "web-provisioning"
}

resource "aws_codedeploy_deployment_group" "web-provisioning-group" {
  app_name = aws_codedeploy_app.web-provisioning.name
  deployment_group_name = "web-group"
  service_role_arn = var.service_role_arn
  ec2_tag_filter {
    key = "Role"
    value = "web"
    type = "KEY_AND_VALUE"
  }
  deployment_config_name = "CodeDeployDefault.AllAtOnce"
}
