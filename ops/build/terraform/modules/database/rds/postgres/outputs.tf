output "rds_hostname" {
  description = "RDS instance hostname"
  value       = aws_db_instance.postgres.address
}

output "rds_port" {
  description = "RDS instance port"
  value       = aws_db_instance.postgres.port
}

output "rds_dbname" {
  description = "RDS instance"
  value = aws_db_instance.postgres.name
}

output "rds_username" {
  description = "RDS instance root username"
  value       = aws_db_instance.postgres.username
}

output "rds_password" {
  description = "RDS instance root password"
  value       = aws_db_instance.postgres.password
}

output "security_group_id" {
  value = aws_security_group.postgres_db_security.id
}

