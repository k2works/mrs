variable "environment" {}
variable "environment_variables" {}

resource "aws_ssm_parameter" "env" {
  overwrite = true
  for_each = var.environment_variables
  name        = "/${var.environment}/${each.key}"
  type        = "String"
  value       = each.value
}
