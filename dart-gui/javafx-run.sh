#!/bin/bash

echo "Starting Dart Gui"

echo "JAVA_HOME=$JAVA_HOME"
echo "JAVAFX_HOME=$JAVAFX_HOME"
echo "DISPLAY=$DISPLAY"

$DISPLAY $JAVA_HOME/bin/java \
--module-path $JAVAFX_HOME/lib:. \
--add-modules javafx.controls,javafx.graphics,javafx.fxml,javafx.media \
--module com.bognandi.dart.dartgame.gui.app/com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport \ 
$@
