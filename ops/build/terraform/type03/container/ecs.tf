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
      # 利用するイメージを変更
      "image" : "${aws_ecr_repository.service.repository_url}:latest",
      "essential" : true,
      "logConfiguration" : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-region" : "ap-northeast-1",
          "awslogs-stream-prefix" : "app-service",
          "awslogs-group" : "/ecs/${var.name}"
        }
      },
      # 環境変数の設定
      "secrets": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "valueFrom": var.environment_variables.SPRING_PROFILES_ACTIVE
        },
        {
          "name": "SPRING_FLYWAY_SCHEMAS",
          "valueFrom": var.environment_variables.SPRING_FLYWAY_SCHEMAS
        },
        {
          "name": "SPRING_DATASOURCE_USERNAME",
          "valueFrom": var.environment_variables.SPRING_DATASOURCE_USERNAME
        },
        {
          "name": "SPRING_DATASOURCE_PASSWORD",
          "valueFrom": var.environment_variables.SPRING_DATASOURCE_PASSWORD
        },
        {
          "name": "SPRING_DATASOURCE_URL",
          "valueFrom": var.environment_variables.SPRING_DATASOURCE_URL
        }
      ],
      "portMappings" : [
        {
          "protocol" : "tcp",
          # アプリケーションの内容に合わせてポート番号を変更
          "containerPort" : 5000,
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
  platform_version                  = "1.4.0"
  health_check_grace_period_seconds = 600

  network_configuration {
    assign_public_ip = false
    security_groups  = [
      var.app_service_sg.security_group_id
    ]

    subnets = [
      var.subnet_private_a.id
    ]
  }

  load_balancer {
    target_group_arn = var.lb_target_group.arn
    container_name   = var.name
    container_port   = 5000
  }

  lifecycle {
    ignore_changes = [task_definition]
  }
}

resource "aws_cloudwatch_log_group" "for_ecs" {
  name              = "/ecs/${var.name}"
  retention_in_days = 180
}

