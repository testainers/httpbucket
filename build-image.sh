#!/bin/sh

set -e

VERSION=$(grep 'version' build.gradle | cut -f 2 -d "'")

if [ ! -f "build/httpbucket-$VERSION-runner" ]
then
  ./gradlew clean

  ./gradlew build -Dquarkus.package.type=native \
                  -Dquarkus.native.container-build=true
fi

docker build . -f src/main/docker/Dockerfile.native-micro \
               -t "httpbucket-local:$VERSION" \
               -t "httpbucket-local:latest"
