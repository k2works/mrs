output "rds_hostname" {
  description = "RDS instance hostname"
  value       = aws_rds_cluster.aurora_serverless.endpoint
}

output "rds_port" {
  description = "RDS instance port"
  value       = aws_rds_cluster.aurora_serverless.port
}

output "rds_dbname" {
  description = "RDS instance"
  value = aws_rds_cluster.aurora_serverless.database_name
}

output "rds_username" {
  description = "RDS instance root username"
  value       = aws_rds_cluster.aurora_serverless.master_username
}

output "rds_password" {
  description = "RDS instance root password"
  value       = aws_rds_cluster.aurora_serverless.master_password
}

output "security_group_id" {
  value = aws_security_group.postgres_db_security.id
}

