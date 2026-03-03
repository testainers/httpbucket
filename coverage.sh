#!/bin/sh

set -e

./gradlew clean test

/bin/cp -rf helpers/jacoco-report/* build/jacoco-report/.

# /opt/google/chrome/google-chrome "file://$(pwd)/build/jacoco-report/index.html"
