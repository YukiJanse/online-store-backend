#!/bin/bash

cd /home/ec2-user/app || exit 0

docker compose down || true