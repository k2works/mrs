resource "aws_iam_policy" "s3" {
  name = "AllowS3Roles"
  description = "Allow S3 access policy"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "s3:*"
      ],
      "Effect": "Allow",
      "Resource": "*"
    }
  ]
}
EOF
}

resource "aws_iam_policy_attachment" "ec2-s3-attach" {
  name = "ec2-s3-attachment"
  roles = [
    aws_iam_role.ec2_role.name]
  policy_arn = aws_iam_policy.s3.arn
}
