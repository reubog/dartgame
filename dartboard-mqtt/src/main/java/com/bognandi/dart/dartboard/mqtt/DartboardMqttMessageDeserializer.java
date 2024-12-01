package com.bognandi.dart.dartboard.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface DartboardMqttMessageDeserializer {
    GranboardMessage deserialize(MqttMessage mqttMessage);
}
