#!/bin/bash

cd /home/ec2-user/app

docker compose --env-file /home/ec2-user/.env pull
docker compose --env-file /home/ec2-user/.env up -d