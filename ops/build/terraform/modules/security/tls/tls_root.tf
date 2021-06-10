resource "tls_private_key" "https_root" {
  algorithm = "RSA"
}

resource "tls_self_signed_cert" "https_root" {
  key_algorithm = tls_private_key.https_root.algorithm
  private_key_pem = tls_private_key.https_root.private_key_pem

  subject {
    common_name = "HTTPS_TEST_ROOT"
  }

  validity_period_hours = 87600

  is_ca_certificate = true

  allowed_uses = [
    "digital_signature",
    "crl_signing",
    "cert_signing",
  ]
}

resource "local_file" "https_root_key" {
  filename = "${var.tls_key_name}_root.key"
  content = tls_private_key.https_root.private_key_pem

  provisioner "local-exec" {
    command = "mv  ./${var.tls_key_name}_root.key ../../config/tls/${var.tls_key_name}_root.key"
  }
}

resource "local_file" "https_root_pem" {
  filename = "${var.tls_key_name}_root.crt"
  content = tls_self_signed_cert.https_root.cert_pem

  provisioner "local-exec" {
    command = "mv  ./${var.tls_key_name}_root.crt ../../config/tls/${var.tls_key_name}_root.crt"
  }
}

