resource "aws_secretsmanager_secret" "postgres_secretmasterDB" {
  name = "MasteraccoundbPostgres"
}

resource "aws_secretsmanager_secret_version" "postgres_sversion" {
  secret_id = aws_secretsmanager_secret.postgres_secretmasterDB.id
  secret_string = <<EOF
   {
    "username": "${var.db_postgres_username}",
    "password": "${random_password.password.result}"
   }
EOF
}

data "aws_secretsmanager_secret" "postgres_secretmasterDB" {
  arn = aws_secretsmanager_secret.postgres_secretmasterDB.arn
}

data "aws_secretsmanager_secret_version" "postgres_creds" {
  secret_id = data.aws_secretsmanager_secret.postgres_secretmasterDB.arn
}

locals {
  db_postgres_creds = jsondecode(
  data.aws_secretsmanager_secret_version.postgres_creds.secret_string
  )
}
