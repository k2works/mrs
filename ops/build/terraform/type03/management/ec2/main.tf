variable "ami" {}
variable "subnet_id" {}
variable "operation_bucket_id" {}
variable "iam_instance_profile" {}

resource "aws_instance" "app_for_operation" {
  ami = var.ami
  instance_type = "t3.micro"
  iam_instance_profile = var.iam_instance_profile
  subnet_id = var.subnet_id
  user_data = file("./management/ec2/user_data.sh")
}

resource "aws_cloudwatch_log_group" "operation" {
  name = "/operation"
  retention_in_days = 180
}

resource "aws_ssm_document" "session_manager_run_shell" {
  name          = "SSM-SessionManagerRunShellOperation"
  document_type = "Session"
  document_format = "JSON"
  content = <<EOF
  {
     "schemaVersion": "1.0",
     "description": "Document to hold regional settings for Session Manager",
     "sessionType": "Standard_Stream",
     "inputs": {
       "s3BucketName": "${var.operation_bucket_id}",
       "cloudWatchLogGroupName": "${aws_cloudwatch_log_group.operation.name}"
     }
  }
EOF
}

output "operation_instance_id" {
  value = aws_instance.app_for_operation.id
}
