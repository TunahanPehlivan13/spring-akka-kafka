#!/usr/bin/env bash

docker-compose down

cd data
mvn clean install -DskipTests

cd ..

cd recorder
mvn clean package -DskipTests

cd ..

cd collector
mvn clean package -DskipTests

cd ..

docker-compose build

docker-compose up