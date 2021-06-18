#!/bin/bash

systemctl start web.service
systemctl daemon-reload
systemctl restart nginx
