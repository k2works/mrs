#!/bin/bash
. $(dirname $0)/common_variables.sh
. $(dirname $0)/paramstore.sh > /srv/mrs/env

cp /srv/mrs/nginx/https.conf /etc/nginx/conf.d
cp /srv/mrs/nginx/http.conf /etc/nginx/conf.d
cp /srv/mrs/nginx/cert.crt /etc/nginx
cp /srv/mrs/nginx/cert.key /etc/nginx

cp /srv/mrs/service/env /srv/mrs
cp /srv/mrs/service/web.service /etc/systemd/system

if [ ! -d /srv/pids ]; then
  mkdir /srv/pids
fi

chown -hR ec2-user:ec2-user /srv/mrs
chown -hR ec2-user:ec2-user /srv/pids
