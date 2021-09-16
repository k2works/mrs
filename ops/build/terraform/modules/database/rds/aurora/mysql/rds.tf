resource "aws_db_subnet_group" "mysql" {
  name       = "${var.app_env_name}-aurora-mysql-db"
  subnet_ids = [var.subnet_id_1, var.subnet_id_2]

  tags = {
    Name = var.app_env_name
  }
}

resource "aws_db_parameter_group" "mysql" {
  name   = "${var.app_env_name}-mysql"
  family = var.db_parameter_group_family

  parameter {
    name  = "character_set_client"
    value = "utf8"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "character_set_connection"
    value = "utf8"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "character_set_database"
    value = "utf8"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "character_set_filesystem"
    value = "utf8"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "character_set_results"
    value = "utf8"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "character_set_server"
    value = "utf8"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "skip-character-set-client-handshake"
    value = "1"
    apply_method = "pending-reboot"
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
  db_subnet_group_name   = aws_db_subnet_group.mysql.name
  skip_final_snapshot    = true
  vpc_security_group_ids = [var.security_group_id]

  scaling_configuration {
    auto_pause               = true
    seconds_until_auto_pause = 300
    max_capacity             = 16
    min_capacity             = 1
    timeout_action           = "ForceApplyCapacityChange"
  }
}

