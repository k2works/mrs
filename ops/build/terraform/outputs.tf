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

output "vpc_id" {
  value = module.app_network.vpc_id
}

output "vpc_cidr" {
  value = module.app_network.vpc_cidr
}

output "vpc_subnet_public" {
  value = module.app_network.vpc_subnet_public-a_id
}

output "vpc_subnet_private" {
  value = module.app_network.vpc_subnet_private-c_id
}


