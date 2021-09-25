#!/bin/sh

amazon-linux-extras install -y docker
systemctl start docker
systemctl enable docker

amazon-linux-extras install -y postgresql11

yum install https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm -y
yum-config-manager –enable mysql57-community
yum install mysql-community-server -y
