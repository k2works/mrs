resource "aws_db_subnet_group" "postgres" {
  name       = "${var.app_env_name}-postgres-db"
  subnet_ids = [var.subnet_id_1, var.subnet_id_2]

  tags = {
    Name = var.app_env_name
  }
}

resource "aws_db_instance" "postgres" {
  identifier             = var.identifier
  instance_class         = var.instance_class
  allocated_storage      = var.allocated_storage
  engine                 = var.engine
  engine_version         = var.engine_version
  name                   = var.db_name
  username               = var.username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.postgres.name
  vpc_security_group_ids = [var.security_group_id]
  publicly_accessible    = true
  skip_final_snapshot    = true
}
