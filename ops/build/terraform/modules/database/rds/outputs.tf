output "rds_hostname" {
  description = "RDS instance hostname"
  value       = aws_db_instance.db.address
}

output "rds_port" {
  description = "RDS instance port"
  value       = aws_db_instance.db.port
}

output "rds_dbname" {
  description = "RDS instance"
  value = aws_db_instance.db.name
}

output "rds_username" {
  description = "RDS instance root username"
  value       = aws_db_instance.db.username
}

output "rds_password" {
  description = "RDS instance root password"
  value       = aws_db_instance.db.password
}
