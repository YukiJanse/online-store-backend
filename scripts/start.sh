#!/bin/bash

cd /home/ec2-user/app

docker compose pull
docker compose --env-file ~/.env up -d