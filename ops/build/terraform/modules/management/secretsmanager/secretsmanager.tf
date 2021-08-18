variable "db_username" {}
variable "db_password" {}

# Firstly we will create a random generated password which we will use in secrets.

resource "random_password" "password" {
  length           = 16
  special          = true
  override_special = "_%@"
}

# Now create secret and secret versions for database master account

resource "aws_secretsmanager_secret" "secretmasterDB" {
  name = "Masteraccoundb"
}

resource "aws_secretsmanager_secret_version" "sversion" {
  secret_id = aws_secretsmanager_secret.secretmasterDB.id
  secret_string = <<EOF
   {
    "username": "${var.db_username}",
    "password": "${random_password.password.result}"
   }
EOF
}


# Lets import the Secrets which got created recently and store it so that we can use later.


data "aws_secretsmanager_secret" "secretmasterDB" {
  arn = aws_secretsmanager_secret.secretmasterDB.arn
}

data "aws_secretsmanager_secret_version" "creds" {
  secret_id = data.aws_secretsmanager_secret.secretmasterDB.arn
}

# After Importing the secrets Storing the Imported Secrets into Locals

locals {
  db_creds = jsondecode(
  data.aws_secretsmanager_secret_version.creds.secret_string
  )
}

output "db_username" {
  value = local.db_creds.username
}

output "db_password" {
  value = local.db_creds.password
}

# https://automateinfra.com/2021/03/24/how-to-create-secrets-in-aws-secrets-manager-using-terraform-in-amazon-account/
