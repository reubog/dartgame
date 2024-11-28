package com.bognandi.dart.dartboard.mqtt;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartgame.Dartboard;
import com.bognandi.dart.core.dartgame.DartboardListener;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.bognandi.dart.dartboard.mqtt.GranboardMqttTopics.GRANBOARD_TOPIC;

public class GranboardMqttSubscriber implements Dartboard, IMqttMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(GranboardMqttSubscriber.class);

    public static GranboardMqttSubscriber createSubscriber(IMqttClient client, GranboardMqttMessageDeserializer deserializer) throws MqttException {
        GranboardMqttSubscriber granboardMqttSubscriber = new GranboardMqttSubscriber(deserializer);
        client.subscribe(GRANBOARD_TOPIC, granboardMqttSubscriber);
        return granboardMqttSubscriber;
    }

    public void unsubscribe(IMqttClient client) throws MqttException {
        client.unsubscribe(GRANBOARD_TOPIC);
    }

    private DartboardStatus status;
    private List<DartboardListener> listeners = new ArrayList<>();
    private GranboardMqttMessageDeserializer deserializer;

    private GranboardMqttSubscriber(GranboardMqttMessageDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public DartboardStatus getStatus() {
        return status;
    }

    @Override
    public void addEventListener(DartboardListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(DartboardListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOG.debug("Message received: {}", new String(mqttMessage.getPayload()));

        if (!GRANBOARD_TOPIC.equals(topic)) {
            return;
        }

        GranboardMessage message = deserializer.deserialize(mqttMessage);

        DartboardStatus oldStatus = status;
        status = message.getStatus();
        if (status != oldStatus) {
            listeners.forEach(listener -> listener.onStatusChange(status));
        }

        if (!DartboardStatus.CONNECTED.equals(status)) {
            return;
        }

        if (message.getValue() != null) {
            listeners.forEach(listener -> listener.onDartboardValue(message.getValue()));
        }
    }
}
