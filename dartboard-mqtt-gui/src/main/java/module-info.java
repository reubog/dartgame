open module com.bognandi.dart.dartgame.gui.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires jakarta.annotation;
    requires spring.core;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires com.bognandi.dart.core;
    requires org.slf4j;
    requires com.bognandi.dart.dartboard.mqtt;
    requires org.eclipse.paho.client.mqttv3;
}