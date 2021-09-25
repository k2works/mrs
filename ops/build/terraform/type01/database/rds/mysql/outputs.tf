output "rds_hostname" {
  description = "RDS instance hostname"
  value       = aws_db_instance.mysql.address
}

output "rds_port" {
  description = "RDS instance port"
  value       = aws_db_instance.mysql.port
}

output "rds_dbname" {
  description = "RDS instance"
  value = aws_db_instance.mysql.name
}

output "rds_username" {
  description = "RDS instance root username"
  value       = aws_db_instance.mysql.username
}

output "rds_password" {
  description = "RDS instance root password"
  value       = aws_db_instance.mysql.password
}

output "security_group_id" {
  value = aws_security_group.mysql_db_security.id
}

