module "http_sg" {
  source      = "./security_group"
  name        = "http-sg"
  vpc_id      = var.vpc_id
  port        = 80
  cidr_blocks = ["0.0.0.0/0"]
}
module "https_sg" {
  source      = "./security_group"
  name        = "https-sg"
  vpc_id      = var.vpc_id
  port        = 443
  cidr_blocks = ["0.0.0.0/0"]
}
module "rds_mysql_sg" {
  source      = "./security_group"
  name        = "rds-mysql-sg"
  vpc_id      = var.vpc_id
  port        = 3306
  cidr_blocks = ["0.0.0.0/0"]
}

data "aws_iam_policy" "ecs_task_execution_role_policy" {
  arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}
data "aws_iam_policy_document" "ecs_task_execution" {
  source_json = data.aws_iam_policy.ecs_task_execution_role_policy.policy
  statement {
    effect    = "Allow"
    actions   = ["ssm:GetParameters", "kms:Decrypt"]
    resources = ["*"]
  }
}
module "ecs_task_execution_role" {
  source     = "./iam_role"
  name       = "ecs-task-execution"
  identifier = "ecs-tasks.amazonaws.com"
  policy     = data.aws_iam_policy_document.ecs_task_execution.json
}
