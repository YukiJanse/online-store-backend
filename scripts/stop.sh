#!/bin/bash

cd /home/ec2-user/app || exit 0

docker compose --env-file /home/ec2-user/.env down || true