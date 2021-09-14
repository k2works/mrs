output "db_connect_postgres" {
  value = "psql -U ${var.db_postgres_username} -h ${module.app_database_postgres.rds_hostname} -p ${module.app_database_postgres.rds_port} -d ${module.app_database_postgres.rds_dbname}"
}

output "application_url" {
  value = "https://${module.app_compute_elastic_beanstalk.app_cname}"
}
