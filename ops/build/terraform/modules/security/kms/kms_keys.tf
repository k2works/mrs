data "aws_caller_identity" "self" {}
variable "org_name" {}
variable "app_name" {}
variable "dev_user_arn" {}
variable "ops_user_arn" {}
variable "ec2_role_arn" {}

resource "aws_kms_key" "app" {
  description = "for application"
  key_usage = "ENCRYPT_DECRYPT"
  enable_key_rotation = true
  deletion_window_in_days = 7
  tags = {
    Name = "${var.org_name}-${var.app_name}"
  }
  policy = <<EOT
{
  "Version": "2012-10-17",
  "Statement": [
    {
        "Sid": "Allow administration of the key",
        "Effect": "Allow",
        "Principal": { "AWS": [
           "arn:aws:iam::${data.aws_caller_identity.self.account_id}:root",
           "${data.aws_caller_identity.self.arn}"
         ]},
        "Action": [
            "kms:Create*",
            "kms:Describe*",
            "kms:Enable*",
            "kms:List*",
            "kms:Put*",
            "kms:Update*",
            "kms:Revoke*",
            "kms:Disable*",
            "kms:Get*",
            "kms:Delete*",
            "kms:ScheduleKeyDeletion",
            "kms:CancelKeyDeletion",
            "kms:TagResource*"
        ],
        "Resource": "*"
    },
    {
      "Sid": "Allow use of the key",
      "Effect": "Allow",
      "Principal": {"AWS": [
        "${var.ops_user_arn}",
        "${var.dev_user_arn}",
        "${var.ec2_role_arn}",
        "arn:aws:iam::${data.aws_caller_identity.self.account_id}:root"
      ]},
      "Action": [
        "kms:Encrypt",
        "kms:Decrypt",
        "kms:ReEncrypt*",
        "kms:GenerateDataKey*",
        "kms:DescribeKey"
      ],
      "Resource": "*"
    },
    {
      "Sid": "Allow attachment of persistent resources",
      "Effect": "Allow",
      "Principal": {"AWS": [
        "${var.ops_user_arn}",
        "${var.dev_user_arn}",
        "${var.ec2_role_arn}",
        "arn:aws:iam::${data.aws_caller_identity.self.account_id}:root"
      ]},
      "Action": [
        "kms:CreateGrant",
        "kms:ListGrants",
        "kms:RevokeGrant"
      ],
      "Resource": "*",
      "Condition": {"Bool": {"kms:GrantIsForAWSResource": true}}
    }
  ]
}
EOT
}

resource "aws_kms_alias" "app" {
  name = "alias/${lower(var.org_name)}-${lower(var.app_name)}"
  target_key_id = aws_kms_key.app.id
}

output "kms_key_alias_name" {
  value = aws_kms_alias.app.name
}
