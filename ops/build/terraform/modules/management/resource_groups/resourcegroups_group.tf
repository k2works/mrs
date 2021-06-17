variable "group_name" {}
variable "environment" {}

resource "aws_resourcegroups_group" "Production" {
  name = var.group_name

  resource_query {
    query = <<JSON
{
  "ResourceTypeFilters": [
    "AWS::EC2::Instance"
  ],
  "TagFilters": [
    {
      "Key": "Environment",
      "Values": ["${var.environment}"]
    }
  ]
}
JSON
  }
}
