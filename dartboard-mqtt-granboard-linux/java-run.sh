#!/bin/bash

echo "Starting Dart Gui"

echo "JAVA_HOME=$JAVA_HOME"

$JAVA_HOME/bin/java \
--module-path . \
--module com.bognandi.dart.dartboard.bluetooth.granboard/com.bognandi.dart.dartboard.bluetooth.granboard.GranboardMqttMain \
$@
