resource "aws_db_subnet_group" "postgres" {
  name       = "${var.app_env_name}-aurora-postgres-db"
  subnet_ids = [var.subnet_id_1, var.subnet_id_2]

  tags = {
    Name = var.app_env_name
  }
}

resource "aws_rds_cluster" "aurora_serverless" {
  cluster_identifier     = var.identifier
  engine_mode            = "serverless"
  engine                 = var.engine
  engine_version         = var.engine_version
  database_name          = var.db_name
  master_username        = var.username
  master_password        = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.postgres.name
  skip_final_snapshot    = true
  vpc_security_group_ids = [var.security_group_id]

  scaling_configuration {
    auto_pause               = true
    seconds_until_auto_pause = 300
    max_capacity             = 16
    min_capacity             = 2
    timeout_action           = "ForceApplyCapacityChange"
  }
}
