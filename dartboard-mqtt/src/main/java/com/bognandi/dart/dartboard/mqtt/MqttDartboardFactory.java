package com.bognandi.dart.dartboard.mqtt;

import com.bognandi.dart.core.dartboard.DartboardFactory;
import com.bognandi.dart.core.dartgame.Dartboard;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttDartboardFactory implements DartboardFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MqttDartboardFactory.class);


    private IMqttClient client;
    private DartboardMqttSubscriber subscriber;
    private DartboardMqttMessageDeserializer deserializer;

    public MqttDartboardFactory(String serverUrl, String clientId) {
        this.deserializer = deserializer;
        LOG.debug("Creating Mqtt factory with clientId={}, serverUrl={}", clientId, serverUrl);
        try {
            client = new MqttClient(serverUrl, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            client.connect(options);

            LOG.info("Connected to MQTT broker: " + serverUrl);

        } catch (MqttException e) {
            LOG.error("Error creating Mqtt factory", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dartboard createDartboard() {
        LOG.debug("Subscribing to Mqtt dartboard");
        try {
            subscriber = DartboardMqttSubscriber.createSubscriber(client, deserializer);
            LOG.info("Subscribed to Mqtt dartboard");
            return subscriber;
        } catch (MqttException e) {
            LOG.warn("Error creating Mqtt subscriber", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroyDartboard(Dartboard dartBoard) {
        LOG.debug("Destroying dartboard");
        try {
            subscriber.unsubscribe(client);
        } catch (MqttException e) {
            LOG.warn("Error destroying Mqtt subscriber", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        LOG.debug("Shutting down Mqtt factory");
    }

}
