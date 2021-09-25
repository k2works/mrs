variable "name" {}
variable "iam_role_arn" {}
variable "buildspec" {}

resource "aws_codebuild_project" "app_build" {
  name         = var.name
  service_role = var.iam_role_arn

  source {
    type      = "CODEPIPELINE"
    buildspec = var.buildspec
  }

  artifacts {
    type = "CODEPIPELINE"
  }

  environment {
    type            = "LINUX_CONTAINER"
    compute_type    = "BUILD_GENERAL1_SMALL"
    image           = "aws/codebuild/standard:5.0"
    privileged_mode = true
  }
}

output "project_id" {
  value = aws_codebuild_project.app_build.id
}
