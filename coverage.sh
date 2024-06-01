#!/bin/sh

set -e

./gradlew clean test

/bin/cp -rf helpers/jacoco-report/* build/jacoco-report/.

# /opt/google/chrome/google-chrome build/coverage/index.html
