module "iam_group_administrator" {
  source = "terraform-aws-modules/iam/aws//modules/iam-group-with-policies"

  name = "administrator"

  group_users = [
    module.iam_user_1.iam_user_name,
  ]

  custom_group_policy_arns = [
    "arn:aws:iam::aws:policy/AdministratorAccess",
  ]
}

module "iam_group_with_developer_policies" {
  source = "terraform-aws-modules/iam/aws//modules/iam-group-with-policies"

  name = "developer"

  group_users = [
    module.iam_user_1.iam_user_name,
    module.iam_user_2.iam_user_name,
  ]

  custom_group_policy_arns = [
    "arn:aws:iam::aws:policy/AmazonEC2FullAccess",
  ]

  custom_group_policies = [
    {
      name = "AllowS3Group"
      policy = aws_iam_policy.s3.policy
    },
  ]
}

