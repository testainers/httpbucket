#!/bin/sh

set -e

VERSION=$(grep 'version =' build.gradle.kts | cut -f 2 -d '"')

if [ ! -f "build/httpbucket-$VERSION-runner" ]
then
  ./gradlew clean

  ./gradlew build -Dquarkus.package.jar.enabled=false \
                  -Dquarkus.native.enabled=true \
                  -Dquarkus.native.compression.level=5
fi

docker build . -f src/main/docker/Dockerfile.native-micro \
               -t "httpbucket-local:$VERSION" \
               -t "httpbucket-local:latest"

docker run --rm --name httpbucket \
           -p 8080:8080 -p 8443:8443 \
           "httpbucket-local:$VERSION"
