#!/bin/bash

cd /home/ec2-user

docker compose pull
docker compose --env-file ~/.env up -d