module "iam_user_1" {
  source = "terraform-aws-modules/iam/aws//modules/iam-user"

  name = "ops@${data.aws_region.current.name}"
  force_destroy = true

  pgp_key = "keybase:k2works"

  password_reset_required = false

  create_iam_user_login_profile = true
  create_iam_access_key = false
}

module "iam_user_2" {
  source = "terraform-aws-modules/iam/aws//modules/iam-user"

  name = "dev@${data.aws_region.current.name}"

  # SSH public key
  upload_iam_user_ssh_key = true

  pgp_key = "keybase:k2works"

  ssh_public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEA0sUjdTEcOWYgQ7ESnHsSkvPUO2tEvZxxQHUZYh9j6BPZgfn13iYhfAP2cfZznzrV+2VMamMtfiAiWR39LKo/bMN932HOp2Qx2la14IbiZ91666FD+yZ4+vhR2IVhZMe4D+g8FmhCfw1+zZhgl8vQBgsRZIcYqpYux59FcPv0lP1EhYahoRsUt1SEU2Gj+jvgyZpe15lnWk2VzfIpIsZ++AeUqyHoJHV0RVOK4MLRssqGHye6XkA3A+dMm2Mjgi8hxoL5uuwtkIsAll0kSfL5O2G26nsxm/Fpcl+SKSO4gs01d9V83xiOwviyOxmoXzwKy4qaUGtgq1hWncDNIVG/aQ=="

  create_iam_user_login_profile = false
  create_iam_access_key = true
}
