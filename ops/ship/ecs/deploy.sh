#!/bin/bash

PROFILE="k2works"
API_URL="https://mrsapplication.k2works-lab.cf/api"
ENDPOINT="686265161912.dkr.ecr.ap-northeast-1.amazonaws.com"
IMAGE="$ENDPOINT/mrsorg_mrsnetwork_mrsapplication:latest"

./gradlew build -x test -Penv=production -Papi=$API_URL
aws ecr get-login-password --region ap-northeast-1 --profile=$PROFILE | docker login --username AWS --password-stdin $ENDPOINT
docker build -f ops/build/docker/java/Dockerfile -t $IMAGE .
docker push $IMAGE
