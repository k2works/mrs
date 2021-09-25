data "aws_elastic_beanstalk_hosted_zone" "current" {}

output "zone_id" {
  value = data.aws_elastic_beanstalk_hosted_zone.current.id
}
output "app_cname" {
  value = aws_elastic_beanstalk_environment.app_production.cname
}

output "app_name" {
  value = aws_elastic_beanstalk_application.app.name
}

output "app_env_name" {
  value = aws_elastic_beanstalk_environment.app_production.name
}
