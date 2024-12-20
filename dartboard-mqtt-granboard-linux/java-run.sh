#!/bin/bash

echo "Starting Dart Gui"

echo "JAVA_HOME=$JAVA_HOME"

$JAVA_HOME/bin/java \
-jar dartboard-mqtt-granboard-linux-0.0.1-SNAPSHOT.jar \
$@