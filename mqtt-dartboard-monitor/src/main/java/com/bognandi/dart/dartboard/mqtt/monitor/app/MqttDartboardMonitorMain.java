package com.bognandi.dart.dartboard.mqtt.monitor.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqttDartboardMonitorMain {
    public static void main(String[] args) {
        SpringApplication.run(MqttDartboardMonitorMain.class, args);
    }
}