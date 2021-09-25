# ECR リポジトリの定義
resource "aws_ecr_repository" "service" {
  name = var.name
}
# ECR ライフサイクルポリシーの定義
resource "aws_ecr_lifecycle_policy" "service_repository" {
  repository = aws_ecr_repository.service.name
  policy     = <<EOF
    {
        "rules": [
            {
                "rulePriority": 1,
                "description": "Expire last 30 release tagged images",
                "selection": {
                    "tagStatus": "tagged",
                    "tagPrefixList": ["release"],
                    "countType": "imageCountMoreThan",
                    "countNumber": 30
                },
                "action": {
                    "type": "expire"
                }
            }
        ]
    }
EOF
}

data "aws_ecr_authorization_token" "token" {}

resource "null_resource" "image_push" {
  provisioner "local-exec" {
    command = "cd ../../../../ && ./gradlew build -x test -Penv=production -Papi=${var.api_url}"
  }
  provisioner "local-exec" {
    command = "cd ../../../../ && docker build -f ops/build/docker/java/Dockerfile -t ${aws_ecr_repository.service.repository_url}:latest ."
  }
  provisioner "local-exec" {
    command = "docker login -u AWS -p ${data.aws_ecr_authorization_token.token.password} ${data.aws_ecr_authorization_token.token.proxy_endpoint}"
  }
  provisioner "local-exec" {
    command = "docker push ${aws_ecr_repository.service.repository_url}"
  }
}
