#!/bin/bash

echo "Starting Dart Gui"

SCRIPT_DIR=$(dirname "$0")

echo "JAVA_HOME=$JAVA_HOME"
echo "JAVAFX_HOME=$JAVAFX_HOME"
echo "DISPLAY=$DISPLAY"
echo "DIR=$SCRIPT_DIR"

$JAVA_HOME/bin/java \
--module-path $JAVAFX_HOME/lib:$SCRIPT_DIR \
--add-modules javafx.controls,javafx.graphics,javafx.fxml,javafx.media,ALL-MODULE-PATH \
--module com.bognandi.dart.dartgame.gui.app/com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport