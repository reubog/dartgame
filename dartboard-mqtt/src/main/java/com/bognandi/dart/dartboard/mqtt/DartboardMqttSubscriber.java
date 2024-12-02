package com.bognandi.dart.dartboard.mqtt;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartgame.Dartboard;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static com.bognandi.dart.dartboard.mqtt.GranboardMqttTopics.GRANBOARD_TOPIC;

public class DartboardMqttSubscriber implements Dartboard, IMqttMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(DartboardMqttSubscriber.class);

    public static DartboardMqttSubscriber createSubscriber(IMqttClient client, DartboardMqttMessageDeserializer deserializer) throws MqttException {
        DartboardMqttSubscriber dartboardMqttSubscriber = new DartboardMqttSubscriber(deserializer);
        client.subscribe(GRANBOARD_TOPIC, dartboardMqttSubscriber);
        return dartboardMqttSubscriber;
    }

    public void unsubscribe(IMqttClient client) throws MqttException {
        client.unsubscribe(GRANBOARD_TOPIC);
    }

    private DartboardStatus status;
    private DartboardMqttMessageDeserializer deserializer;
    private Consumer<DartboardStatus> statusConsumer;
    private Consumer<DartboardValue> valueConsumer;
    private Queue<GranboardMessage> messageQueue = new ArrayDeque<>();
    private ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    private DartboardMqttSubscriber(DartboardMqttMessageDeserializer deserializer) {
        this.deserializer = deserializer;

        executorService.execute(() -> {
            LOG.debug("Starting message processing thread");
            while (true) {
                try {
                    GranboardMessage message = messageQueue.poll();
                    if (message != null) synchronized (messageQueue) {
                        processMessage(message);
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public DartboardStatus getStatus() {
        return status;
    }

    @Override
    public void setOnStatusChange(Consumer<DartboardStatus> statusConsumer) {
        this.statusConsumer = statusConsumer;
    }

    @Override
    public void setOnDartboardValue(Consumer<DartboardValue> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        LOG.trace("Message received from topic {}: {}", topic, new String(mqttMessage.getPayload()));

        if (!GRANBOARD_TOPIC.equals(topic)) {
            return;
        }

        synchronized (messageQueue) {
            messageQueue.add(deserializer.deserialize(mqttMessage));
            LOG.trace("Message added to queue: size={}", messageQueue.size());
        }
    }

    private void processMessage(GranboardMessage message) {
        LOG.trace("Processing message: {}", message);
        DartboardStatus oldStatus = status;
        status = message.getStatus();
        if (status != oldStatus) {
            if (statusConsumer != null) statusConsumer.accept(status);
        }

        if (!DartboardStatus.CONNECTED.equals(status)) {
            return;
        }

        if (message.getValue() != null) {
            if (valueConsumer != null) valueConsumer.accept(message.getValue());
        }
    }
}
