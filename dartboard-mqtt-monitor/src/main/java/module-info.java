open module com.bognandi.dart.dartboard.mqtt.monitor.app {
    requires org.slf4j;
    requires com.bognandi.dart.core;
    requires com.bognandi.dart.dartboard.mqtt;
    requires jakarta.annotation;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.core;
    requires spring.boot.autoconfigure;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.eclipse.paho.client.mqttv3;
}