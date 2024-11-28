package com.bognandi.dart.dartboard.mqtt.monitor.app;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartgame.DartboardListener;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttMessageDeserializer;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttSubscriber;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MonitorService {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MonitorService.class);

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    private GranboardMqttMessageDeserializer deserializer;

    private GranboardMqttSubscriber subscriber;

    public MonitorService() {
        LOG.info("Monitor service startar...");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listenToBroker() {
        LOG.info("Subscribing to broker");
        try {
            subscriber = GranboardMqttSubscriber.createSubscriber(mqttClient, deserializer);
            subscriber.addEventListener(new Listener());
            LOG.info("Subscriber connected");

        } catch (MqttException e) {
            LOG.warn("Failed to connect subscriber");
            throw new RuntimeException(e);
        }
    }

    private class Listener implements DartboardListener {
        @Override
        public void onStatusChange(DartboardStatus status) {
            LOG.info("Status: {}", status);
        }

        @Override
        public void onDartboardValue(DartboardValue value) {
            LOG.info("Value: {}", value);
        }
    }
}
