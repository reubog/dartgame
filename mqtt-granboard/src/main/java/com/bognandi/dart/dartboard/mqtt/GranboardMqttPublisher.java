package com.bognandi.dart.dartboard.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.bognandi.dart.dartboard.mqtt.GranboardMqttTopics.GRANBOARD_TOPIC;

public class GranboardMqttPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(GranboardMqttPublisher.class);

    private GranboardMqttMessageSerializer serializer;
    private IMqttClient client;

    public GranboardMqttPublisher(IMqttClient client, GranboardMqttMessageSerializer serializer) {
        this.serializer = serializer;
        this.client = client;
    }

    public void publish(GranboardMessage message) {
        try {
            MqttMessage mqttMessage = serializer.serialize(message);
            client.publish(GRANBOARD_TOPIC, mqttMessage);
            LOG.info("Message published: {}", new String(mqttMessage.getPayload()));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
