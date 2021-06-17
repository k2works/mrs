output "iam_user_1_arn" {
  value = module.iam_user_1.iam_user_arn
}
output "iam_user_1_key_id" {
  value = module.iam_user_1.iam_access_key_id
}
output "iam_user_1_key_secret" {
  value = module.iam_user_1.iam_access_key_encrypted_secret
}

output "iam_user_2_arn" {
  value = module.iam_user_2.iam_user_arn
}
output "iam_user_2_key_id" {
  value = module.iam_user_2.iam_access_key_id
}
output "iam_user_2_key_secret" {
  value = module.iam_user_2.iam_access_key_encrypted_secret
}

output "iam_role_ec2_arn" {
  value = aws_iam_role.ec2_role.arn
}

output "iam_instance_profile_ec2" {
  value = aws_iam_instance_profile.ec2_profile.name
}

output "iam_role_codedploy_arn" {
  value = aws_iam_role.codedeploy_role.arn
}
