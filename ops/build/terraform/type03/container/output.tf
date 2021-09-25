output "ecs_cluster_name" {
  value = aws_ecs_cluster.app_container.name
}

output "ecs_service_name" {
  value = aws_ecs_service.app_container_service.name
}

output "log_group" {
  value = aws_cloudwatch_log_group.for_ecs
}
