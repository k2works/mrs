output "apprunner_service_url" {
  value = "https://${module.app_compute_apprunner.service_url}"
}

output "amplify_service_url" {
  value = "https://${module.app_mobile_amplify.amplify_service_url}"
}

output "db_connect_postgres" {
  value = "psql -U ${var.db_postgres_username} -h ${module.app_database_postgres.rds_hostname} -p ${module.app_database_postgres.rds_port} -d ${module.app_database_postgres.rds_dbname}"
}

