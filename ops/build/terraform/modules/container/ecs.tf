# ECS クラスター
resource "aws_ecs_cluster" "example" {
  name = "example"
}
# タスク定義
resource "aws_ecs_task_definition" "example" {
  family                   = "app-example"
  cpu                      = 256
  memory                   = 1024
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  container_definitions = jsonencode([
    {
      "name" : "example",
      # 利用するイメージを変更
      "image" : "${aws_ecr_repository.example.repository_url}:latest",
      "essential" : true,
      "logConfiguration" : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-region" : "ap-northeast-1",
          "awslogs-stream-prefix" : "tf-example",
          "awslogs-group" : "/ecs/example"
        }
      },
      # 環境変数の設定
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": var.environment_variables.SPRING_PROFILES_ACTIVE
        },
        {
          "name": "SPRING_FLYWAY_SCHEMAS",
          "value": var.environment_variables.SPRING_FLYWAY_SCHEMAS
        },
        {
          "name": "SPRING_DATASOURCE_USERNAME",
          "value": var.environment_variables.SPRING_DATASOURCE_USERNAME
        },
        {
          "name": "SPRING_DATASOURCE_PASSWORD",
          "value": var.environment_variables.SPRING_DATASOURCE_PASSWORD
        },
        {
          "name": "SPRING_DATASOURCE_URL",
          "value": var.environment_variables.SPRING_DATASOURCE_URL
        }
      ],
      "portMappings" : [
        {
          "protocol" : "tcp",
          # アプリケーションの内容に合わせてポート番号を変更
          "containerPort" : 5000
        }
      ]
    }
  ])
  execution_role_arn = module.ecs_task_execution_role.iam_role_arn
  task_role_arn      = aws_iam_role.ecs_task.arn  # 新規追加した行
}
# ECS サービス
resource "aws_ecs_service" "example" {
  name                              = "example"
  cluster                           = aws_ecs_cluster.example.arn
  task_definition                   = aws_ecs_task_definition.example.arn
  desired_count                     = 2
  launch_type                       = "FARGATE"
  platform_version                  = "1.4.0"
  health_check_grace_period_seconds = 60
  network_configuration {
    assign_public_ip = false
    security_groups  = [module.tf-example_sg.security_group_id, module.tf-example_rds_mysql_sg.security_group_id]
    subnets = [
      var.subnet_private_a_id,
      var.subnet_private_c_id,
    ]
  }
  load_balancer {
    target_group_arn = aws_lb_target_group.ecs.arn
    container_name   = "example"
    # アプリケーションの内容に合わせてポート番号を変更
    container_port   = 5000
  }
  lifecycle {
    ignore_changes = [task_definition]
  }
}
module "tf-example_sg" {
  source      = "./security_group"
  name        = "tf-example-sg"
  vpc_id      = var.vpc_id
  # アプリケーションの内容に合わせてポート番号を変更
  port        = 5000
  cidr_blocks = [var.cidr_block]
}
module "tf-example_rds_mysql_sg" {
  source      = "./security_group"
  name        = "tf-example-rds-mysql-sg"
  vpc_id      = var.vpc_id
  # アプリケーションの内容に合わせてポート番号を変更
  port        = 3306
  cidr_blocks = [var.cidr_block]
}
# 以下、新規追加した箇所
# ECS タスク定義に付与する IAM ロール
data "aws_iam_policy_document" "ecs_assume_role" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}
resource "aws_iam_role" "ecs_task" {
  name               = "tf_example-ecs_task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_assume_role.json
}
resource "aws_iam_role_policy_attachment" "ecs_service" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceRole"
  role       = aws_iam_role.ecs_task.name
}
resource "aws_iam_role_policy_attachment" "rds-full-access" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonRDSFullAccess"
  role       = aws_iam_role.ecs_task.name
}
