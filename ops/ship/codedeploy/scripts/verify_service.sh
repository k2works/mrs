#!/bin/bash

# Deploy hooks are run via absolute path, so taking dirname of this script will give us the path to
# our deploy_hooks directory.
. $(dirname $0)/common_variables.sh

sleep 1m
curl -s http://localhost:5000
