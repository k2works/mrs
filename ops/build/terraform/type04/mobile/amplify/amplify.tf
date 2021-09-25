variable "app_name" {}
variable "app_repository" {}
variable "app_api_url" {}
variable "access_token" {}
variable "domain" {}

resource "aws_amplify_app" "app" {
  name       = var.app_name
  repository = var.app_repository

  # GitHub personal access token
  access_token = var.access_token

  # The default build_spec added by the Amplify Console for React.
  build_spec = <<-EOT
    version: 1
    frontend:
      phases:
        preBuild:
          commands:
            - npm ci
        build:
          commands:
            - npm config set Mrs:production_api_url $PRD_API_URL
            - npm run build:prd
      artifacts:
        baseDirectory: src/main/resources/public
        files:
          - '**/*'
      cache:
        paths:
          - node_modules/**/*
    test:
      artifacts:
        baseDirectory: cypress
        configFilePath: '**/mochawesome.json'
        files:
          - '**/*.png'
          - '**/*.mp4'
      phases:
        preTest:
          commands:
            - npm ci
            - npm install wait-on
            - npm install pm2
            - npm install mocha mochawesome mochawesome-merge mochawesome-report-generator
            - npm config set Mrs:development_api_url $DEV_API_URL
            - npx pm2 start npm -- start
            - 'npx wait-on http://localhost:3000'
        test:
          commands:
            - npm run test:unit
            - 'npx cypress run --reporter mochawesome --reporter-options "reportDir=cypress/report/mochawesome-report,overwrite=false,html=false,json=true,timestamp=mmddyyyy_HHMMss"'
        postTest:
          commands:
            - npx mochawesome-merge cypress/report/mochawesome-report/mochawesome*.json > cypress/report/mochawesome.json
            - npx pm2 kill
  EOT

  # The default rewrites and redirects added by the Amplify Console.
  custom_rule {
    source = "/<*>"
    status = "404"
    target = "/index.html"
  }
}

resource "aws_amplify_branch" "develop" {
  app_id      = aws_amplify_app.app.id
  branch_name = "develop"

  framework = "React"
  stage     = "PRODUCTION"

  environment_variables = {
    PRD_API_URL = var.app_api_url,
    DEV_API_URL = var.app_api_url
  }
}

resource "aws_amplify_domain_association" "domain" {
  app_id      = aws_amplify_app.app.id
  domain_name = var.domain

  sub_domain {
    branch_name = aws_amplify_branch.develop.branch_name
    prefix      = ""
  }

  sub_domain {
    branch_name = aws_amplify_branch.develop.branch_name
    prefix      = "www"
  }
}

output "amplify_service_url" {
  value = aws_amplify_domain_association.domain.domain_name
}
