#!/bin/bash
. $(dirname $0)/common_variables.sh

cp /srv/mrs/nginx/https.conf /etc/nginx/conf.d
cp /srv/mrs/nginx/cert.crt /etc/nginx
cp /srv/mrs/nginx/cert.key /etc/nginx

cp /srv/mrs/service/env /srv/mrs
cp /srv/mrs/service/web.service /etc/systemd/system

if [ ! -d /var/pids ]; then
  mkdir /var/pids
fi
