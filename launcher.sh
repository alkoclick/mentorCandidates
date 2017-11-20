#!/bin/bash

./gradlew clean buildDocker

docker-compose up --build -d