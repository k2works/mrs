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

output "vpc_subnet_public_a_id" {
  value = module.app_network.vpc_subnet_public-a_id
}

output "vpc_subnet_public_c_id" {
  value = module.app_network.vpc_subnet_public-c_id
}

output "vpc_subnet_private_a_id" {
  value = module.app_network.vpc_subnet_private-a_id
}

output "vpc_subnet_private_c_id" {
  value = module.app_network.vpc_subnet_private-c_id
}

output "rds_security_group_id" {
  value = module.app_compute_security.db_security_group_id
}

output "ec2_security_group_id" {
  value = module.app_compute_security.app_security_group_id
}

output "ec2_ssh_key_name" {
  value = module.app_compute_ec2.ssh_key_name
}

output "ec2_instance_id" {
  value = module.app_compute_ec2.instance_id
}

output "ec2_instance_public_ip" {
  value = module.app_compute_ec2.public_ip
}

output "ec2_instance_private_ip" {
  value = module.app_compute_ec2.private_ip
}
output "app_cname" {
  value = "http://${module.app_compute_elastic_beanstalk.app_cname}"
}
output "db_connect" {
  value = "mysql -u ${module.app_database.rds_username} -h ${module.app_database.rds_hostname} -P ${module.app_database.rds_port} ${module.app_database.rds_dbname}"
}
