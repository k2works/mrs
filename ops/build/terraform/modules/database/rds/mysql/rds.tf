resource "aws_db_subnet_group" "db" {
  name       = "${var.app_env_name}-db"
  subnet_ids = [var.subnet_id_1, var.subnet_id_2]

  tags = {
    Name = var.app_env_name
  }
}

resource "aws_db_parameter_group" "db" {
  name   = var.app_env_name
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

resource "aws_db_instance" "db" {
  identifier             = var.identifier
  instance_class         = var.instance_class
  allocated_storage      = var.allocated_storage
  engine                 = var.engine
  engine_version         = var.engine_version
  name                   = var.db_name
  username               = var.username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.db.name
  vpc_security_group_ids = [var.security_group_id]
  parameter_group_name   = aws_db_parameter_group.db.name
  publicly_accessible    = true
  skip_final_snapshot    = true
}
