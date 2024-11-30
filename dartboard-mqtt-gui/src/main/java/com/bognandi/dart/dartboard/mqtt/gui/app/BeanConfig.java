package com.bognandi.dart.dartboard.mqtt.gui.app;

import com.bognandi.dart.dartboard.mqtt.GranboardMqttMessageSerializer;
import com.bognandi.dart.dartboard.mqtt.MqttClientFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MqttClientFactory createClientFactory(
            @Value("${dartboard.mqtt.serverUrl}") String serverUrl,
            @Value("${dartboard.mqtt.clientId}") String clientId
    ) {
        return new MqttClientFactory(serverUrl, clientId);
    }

    @Bean(destroyMethod = "disconnect")
    public IMqttClient createMqttClient(@Autowired MqttClientFactory clientFactory) {
        return clientFactory.newConnectedClient();
    }

    @Bean
    public GranboardMqttMessageSerializer createSerializer(@Autowired ObjectMapper objectMapper) {
        return message -> {
                try {
                    return new MqttMessage(objectMapper.writeValueAsBytes(message));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            };
    }
}
