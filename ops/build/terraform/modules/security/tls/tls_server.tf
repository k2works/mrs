resource "tls_private_key" "https" {
  algorithm = "RSA"
}

resource "tls_cert_request" "https" {
  key_algorithm = tls_private_key.https.algorithm
  private_key_pem = tls_private_key.https.private_key_pem

  subject {
    common_name = "*.ap-northeast-1.elb.amazonaws.com"
  }

  dns_names = [
    "*.ap-northeast-1.elb.amazonaws.com",
  ]
}

resource "tls_locally_signed_cert" "https" {
  cert_request_pem = tls_cert_request.https.cert_request_pem

  ca_key_algorithm = tls_private_key.https_root.algorithm
  ca_private_key_pem = tls_private_key.https_root.private_key_pem
  ca_cert_pem = tls_self_signed_cert.https_root.cert_pem

  validity_period_hours = 87600

  is_ca_certificate = false
  set_subject_key_id = true

  allowed_uses = [
    "key_encipherment",
    "digital_signature",
    "server_auth",
    "client_auth",
  ]
}

resource "local_file" "https_test_key" {
  filename = "${var.tls_key_name}.key"
  content = tls_private_key.https.private_key_pem

  provisioner "local-exec" {
    command = "mv  ./${var.tls_key_name}.key ../../config/tls/${var.tls_key_name}.key"
  }
}

resource "local_file" "https_test_cert_pem" {
  filename = "${var.tls_key_name}.crt"
  content = tls_locally_signed_cert.https.cert_pem

  provisioner "local-exec" {
    command = "mv  ./${var.tls_key_name}.crt ../../config/tls/${var.tls_key_name}.crt"
  }
}
