#!/bin/bash

systemctl stop web.service
systemctl daemon-reload
systemctl start web.service
systemctl restart nginx
