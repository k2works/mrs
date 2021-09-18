resource "aws_ecs_cluster" "app_container" {
  name = var.name
}

resource "aws_ecs_task_definition" "app_container_task" {
  family                   = var.name
  cpu                      = 256
  memory                   = 512
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = var.ecs_task_execution_role.iam_role_arn

  container_definitions = jsonencode([
    {
      "name" : "${var.name}",
      "image" : "nginx:latest",
      "essential" : true,
      "logConfiguration" : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-region" : "ap-northeast-1",
          "awslogs-stream-prefix" : "nginx",
          "awslogs-group" : "/ecs/${var.name}"
        }
      },
      "portMappings" : [
        {
          "protocol" : "tcp",
          "containerPort" : 80
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "app_container_service" {
  name                              = var.name
  cluster                           = aws_ecs_cluster.app_container.arn
  task_definition                   = aws_ecs_task_definition.app_container_task.arn
  desired_count                     = 2
  launch_type                       = "FARGATE"
  platform_version                  = "1.3.0"
  health_check_grace_period_seconds = 60

  network_configuration {
    assign_public_ip = false
    security_groups  = [var.nginx_sg.security_group_id]

    subnets = [
      var.subnet_private_a.id,
      var.subnet_private_c.id
    ]
  }

  load_balancer {
    target_group_arn = var.lb_target_group.arn
    container_name   = var.name
    container_port   = 80
  }

  lifecycle {
    ignore_changes = [task_definition]
  }
}

resource "aws_cloudwatch_log_group" "for_ecs" {
  name              = "/ecs/${var.name}"
  retention_in_days = 180
}

