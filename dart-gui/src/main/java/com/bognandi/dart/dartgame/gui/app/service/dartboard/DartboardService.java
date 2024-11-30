package com.bognandi.dart.dartgame.gui.app.service.dartboard;

import com.bognandi.dart.core.dartgame.Dartboard;
import com.bognandi.dart.dartboard.mqtt.DartboardMqttSubscriber;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttMessageDeserializer;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DartboardService {

    private static final Logger LOG = LoggerFactory.getLogger(DartboardService.class);

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    private GranboardMqttMessageDeserializer deserializer;

    private DartboardMqttSubscriber subscriber;

    public DartboardService() {
        LOG.info("Constructing service");
    }

    public Dartboard getDartboard() {
        LOG.info("Creating dartboard");
        try {
            subscriber = DartboardMqttSubscriber.createSubscriber(mqttClient, deserializer);
            return subscriber;
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


    @PreDestroy
    public void destroy() {
        LOG.info("unsubscribiting");
        try {
            subscriber.unsubscribe(mqttClient);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


}
