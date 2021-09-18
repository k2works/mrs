variable "name" {}
variable "role_arn" {}
variable "full_repository_id" {}
variable "blanch_name" {}
variable "codebuild_project_name" {}
variable "ecs_cluster_name" {}
variable "ecs_service_name" {}
variable "deploy_bucket_name" {}

resource "aws_codepipeline" "app_pipeline" {
  name     = var.name
  role_arn = var.role_arn
  stage {
    name = "Source"

    action {
      name             = "Source"
      category         = "Source"
      owner            = "AWS"
      provider         = "CodeStarSourceConnection"
      version          = "1"
      output_artifacts = ["Source"]

      configuration = {
        ConnectionArn    = aws_codestarconnections_connection.app_connection.arn
        FullRepositoryId = var.full_repository_id
        BranchName       = var.blanch_name
      }
    }
  }

  stage {
    name = "Build"

    action {
      name             = "Build"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      version          = 1
      input_artifacts  = ["Source"]
      output_artifacts = ["Build"]

      configuration = {
        ProjectName = var.codebuild_project_name
      }
    }
  }

  stage {
    name = "Deploy"

    action {
      name            = "Deploy"
      category        = "Deploy"
      owner           = "AWS"
      provider        = "ECS"
      version         = 1
      input_artifacts = ["Build"]

      configuration = {
        ClusterName = var.ecs_cluster_name
        ServiceName = var.ecs_service_name
        FileName    = "imagedefinitions.json"
      }
    }
  }

  artifact_store {
    location = var.deploy_bucket_name
    type     = "S3"
  }
}

resource "aws_codestarconnections_connection" "app_connection" {
  name          = "app-connection"
  provider_type = "GitHub"
}
