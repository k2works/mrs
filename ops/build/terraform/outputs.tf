output "iam_user_1_key_id" {
  value = module.app_security_iam.iam_user_1_key_id
}
output "iam_user_1_key_secret" {
  value = module.app_security_iam.iam_user_1_key_secret
}

output "iam_user_2_key_id" {
  value = module.app_security_iam.iam_user_2_key_id
}
output "iam_user_2_key_secret" {
  value = module.app_security_iam.iam_user_2_key_secret
}

output "kms_key_name" {
  value = module.app_security_kms.kms_key_alias_name
}

output "vpc_id" {
  value = module.app_network.vpc_id
}

output "vpc_cidr" {
  value = module.app_network.vpc_cidr
}

output "vpc_subnet_public_id" {
  value = module.app_network.vpc_subnet_public-a_id
}

output "vpc_subnet_private_id" {
  value = module.app_network.vpc_subnet_private-c_id
}

output "ec2_security_group_id" {
  value = module.app_compute_security.security_group_id
}

output "ec2_ssh_key_name" {
  value = module.app_compute_ec2.ssh_key_name
}
