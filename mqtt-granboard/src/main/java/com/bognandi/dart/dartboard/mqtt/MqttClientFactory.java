package com.bognandi.dart.dartboard.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class MqttClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MqttClientFactory.class);

    private String serverUrl;
    private String clientId;
    private Set<IMqttClient> clients = new HashSet<>();

    public MqttClientFactory(String serverUrl, String clientId) {
        LOG.debug("Creating Mqtt factory with clientId={}, serverUrl={}", clientId, serverUrl);
        this.serverUrl = serverUrl;
        this.clientId = clientId;
    }

    public IMqttClient newConnectedClient() {
        try {
            LOG.debug("Creating new connection...");

            IMqttClient client = new MqttClient(serverUrl, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            client.connect(options);

            LOG.info("Connected to MQTT broker: " + serverUrl);

            return client;

        } catch (MqttException e) {
            LOG.error("Error creating Mqtt factory", e);
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        LOG.debug("Shutting down Mqtt factory");
        clients.forEach(client -> {
            try {
                client.disconnect(5000);
            } catch (MqttException e) {
                LOG.warn("Error disconnecting Mqtt client", e);
            }
        });
    }

}
