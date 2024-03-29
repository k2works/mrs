resource "aws_elastic_beanstalk_application" "app" {
  name = var.app_name
  description = var.app_description

  appversion_lifecycle {
    service_role = var.service_role
    max_count = 128
    delete_source_from_s3 = true
  }
}
