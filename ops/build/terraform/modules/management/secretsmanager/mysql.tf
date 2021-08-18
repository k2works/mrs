resource "aws_secretsmanager_secret" "mysql_secretmasterDB" {
  name = "MasteraccoundbMySQL"
}

resource "aws_secretsmanager_secret_version" "mysql_sversion" {
  secret_id = aws_secretsmanager_secret.mysql_secretmasterDB.id
  secret_string = <<EOF
   {
    "username": "${var.db_mysql_username}",
    "password": "${random_password.password.result}"
   }
EOF
}

data "aws_secretsmanager_secret" "mysql_secretmasterDB" {
  arn = aws_secretsmanager_secret.mysql_secretmasterDB.arn
}

data "aws_secretsmanager_secret_version" "mysql_creds" {
  secret_id = data.aws_secretsmanager_secret.mysql_secretmasterDB.arn
}

locals {
  db_mysql_creds = jsondecode(
  data.aws_secretsmanager_secret_version.mysql_creds.secret_string
  )
}
