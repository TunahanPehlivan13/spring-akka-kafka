#!/usr/bin/env bash
cd recorder
mvn clean package -DskipTests

cd ..

cd collector
mvn clean package -DskipTests

docker-compose build

docker-compose up

cmd /k