output "iam_user_1_key_id" {
  value = module.iam_user_1.iam_access_key_id
}
output "iam_user_1_key_secret" {
  value = module.iam_user_1.iam_access_key_encrypted_secret
}

output "iam_user_2_key_id" {
  value = module.iam_user_2.iam_access_key_id
}
output "iam_user_2_key_secret" {
  value = module.iam_user_2.iam_access_key_encrypted_secret
}


