#!/bin/sh

set -e

export QUARKUS_JACOCO_REPORT_LOCATION='coverage'
export QUARKUS_JACOCO_FOOTER='httpbucket'
export QUARKUS_JACOCO_TITLE='httpbucket'
export QUARKUS_JACOCO_EXCLUDES='**/SimpleHealthCheck.class'

./gradlew cleanTest test

/bin/cp -rf helpers/coverage/* build/coverage/.

/opt/google/chrome/google-chrome build/coverage/index.html
