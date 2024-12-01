package com.bognandi.dart.dartboard.mqtt.monitor.app;

import com.bognandi.dart.dartboard.mqtt.DartboardMqttSubscriber;
import com.bognandi.dart.dartboard.mqtt.DartboardMqttMessageDeserializer;
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
    private DartboardMqttMessageDeserializer deserializer;

    private DartboardMqttSubscriber subscriber;

    public MonitorService() {
        LOG.info("Monitor service startar...");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listenToBroker() {
        LOG.info("Subscribing to broker");
        try {
            subscriber = DartboardMqttSubscriber.createSubscriber(mqttClient, deserializer);
            subscriber.setOnStatusChange(status -> LOG.info("Status: {}", status));
            subscriber.setOnDartboardValue(value -> LOG.info("Value: {}", value));
            LOG.info("Subscriber connected");

        } catch (MqttException e) {
            LOG.warn("Failed to connect subscriber");
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void destroy() {
        LOG.info("unsubscribing");
        try {
            subscriber.unsubscribe(mqttClient);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
