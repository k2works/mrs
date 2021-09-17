output "alb_dns_name" {
  value = "http://${module.app_network_loadbalancer.alb_dns_name}"
}

output "db_connect_mysql" {
  value = "mysql -u ${var.db_mysql_username} -h ${module.app_database_serverless_mysql.rds_hostname} -P ${module.app_database_serverless_mysql.rds_port} ${module.app_database_serverless_mysql.rds_dbname}"
}

output "application_url" {
  value = "https://${module.app_network_dns.domain_name}"
}
