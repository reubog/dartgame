package com.bognandi.dart.dartgame.gui.app.config;

import com.bognandi.dart.core.dartgame.DartValueMapper;
import com.bognandi.dart.core.dartgame.DefaultDartValueMapper;
import com.bognandi.dart.dartboard.mqtt.GranboardMessage;
import com.bognandi.dart.dartboard.mqtt.DartboardMqttMessageDeserializer;
import com.bognandi.dart.dartboard.mqtt.MqttClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeanConfig {

    @Bean
    public DartValueMapper createDartValueMapper() {
        return new DefaultDartValueMapper();
    }

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
    public DartboardMqttMessageDeserializer createDeserializer(@Autowired ObjectMapper objectMapper) {
        return message -> {
            try {
                return objectMapper.readValue(message.getPayload(), GranboardMessage.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
