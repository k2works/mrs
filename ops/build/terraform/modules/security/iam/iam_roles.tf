data "aws_caller_identity" "iam" {}

module "iam_assumable_roles" {
  source = "terraform-aws-modules/iam/aws//modules/iam-assumable-roles"
  version = "~> 3.0"

  trusted_role_arns = [
    "arn:aws:iam::${data.aws_caller_identity.iam.account_id}:root",
  ]

  create_admin_role = true

  create_poweruser_role = true
  poweruser_role_name = "developer"

  create_readonly_role = true
  readonly_role_requires_mfa = false
}

resource "aws_iam_role" "ec2_role" {
  name = "ec2-role"

  # Terraform's "jsonencode" function converts a
  # Terraform expression result to valid JSON syntax.
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Sid = ""
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      },
    ]
  })

  tags = {
    tag-key = "ec2"
  }
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = aws_iam_role.ec2_role.name
}
