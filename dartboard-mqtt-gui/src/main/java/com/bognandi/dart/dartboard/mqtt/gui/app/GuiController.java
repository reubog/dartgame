package com.bognandi.dart.dartboard.mqtt.gui.app;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.dartboard.mqtt.GranboardMessage;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttMessageSerializer;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttPublisher;
import jakarta.annotation.PreDestroy;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuiController {

    private static final Logger LOG = LoggerFactory.getLogger(GuiController.class);

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    private GranboardMqttMessageSerializer serializer;

    private GranboardMqttPublisher publisher;

    @FXML
    private ChoiceBox<DartboardValue> dartvalueChoice;

    @FXML
    public void sendDartvalue() {
        LOG.info("Sending dart value: {}", dartvalueChoice.getValue());
        publisher.publish(new GranboardMessage(DartboardStatus.CONNECTED, dartvalueChoice.getValue()));
    }

    public void initialize() {
        LOG.info("Initializing GUI controller");
        publisher = new GranboardMqttPublisher(mqttClient, serializer);
        dartvalueChoice.getItems().addAll(DartboardValue.values());
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Destroying GUI controller");
        publisher.publish(new GranboardMessage(DartboardStatus.DISCONNECTED, null));
    }
}
