package com.bognandi.dart.dartboard.bluetooth.app;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.dartboard.BluetoothDartboardPeripheral;
import com.bognandi.dart.dartboard.bluetooth.BluetoothSearchCallback;
import com.bognandi.dart.dartboard.bluetooth.LoggedBluetoothCallback;
import com.bognandi.dart.dartboard.mqtt.GranboardMessage;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttMessageSerializer;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttPublisher;
import com.bognandi.dart.dartboard.mqtt.MqttClientFactory;
import com.welie.blessed.*;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BluetoothDartboardService {

    private static final Logger LOG = LoggerFactory.getLogger(BluetoothDartboardService.class);

    private final BluetoothCentralManager centralManager;
    private BluetoothSearchCallback centralManagerCallback;
    private BluetoothPeripheralCallback peripheralCallback;
    private boolean shuttingDown;

    public BluetoothDartboardService(
            @Value("${dartboard.bluetooth.name}") String scanForName,
            @Autowired GranboardMqttMessageSerializer serializer,
            @Autowired IMqttClient mqttClient
    ) {
        LOG.info("Starting BluetoothDartboardFactory");

        GranboardMqttPublisher publisher = new GranboardMqttPublisher(mqttClient, serializer);
        peripheralCallback = new BluetoothDartboardPeripheral(publisher);

        centralManagerCallback = new BluetoothSearchCallback();
        centralManager = new BluetoothCentralManager(centralManagerCallback);
        centralManagerCallback.setPeripheralName(scanForName);
        centralManagerCallback.setOnDiscovered(peripheral -> {
            centralManager.stopScan();
            centralManager.connectPeripheral(peripheral, peripheralCallback);
        });
        centralManagerCallback.setOnConnected(peripheral -> centralManager.stopScan());
        centralManagerCallback.setOnDisconnected(peripheral -> {
            publisher.publish(new GranboardMessage(DartboardStatus.DISCONNECTED, null));
            scan(scanForName);
        });
        centralManager.adapterOn();
        centralManager.setRssiThreshold(-120);

        scan(scanForName);
    }

    public synchronized void scan(String name) {
        if (shuttingDown) {
            LOG.info("Ignoring scanning becasuse shutting down");
            return;
        }

        LOG.info("Scanning for device name={}", name);
        centralManager.scanForPeripheralsWithNames(new String[]{name});
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("Shutting down BluetoothDartboardFactory");
        shuttingDown = true;

        centralManager.stopScan();

        centralManager.getConnectedPeripherals().forEach(peripheral -> {
            peripheral.getNotifyingCharacteristics().forEach(cstic -> {
                peripheral.setNotify(cstic.getService().getUuid(), cstic.getUuid(), false);
            });
            waitSeconds(1);
            peripheral.cancelConnection();
            LOG.info("Peripheral disconnected: name={}, address={}", peripheral.getName(), peripheral.getAddress());
        });
        waitSeconds(5);
        centralManager.shutdown();
        LOG.info("Bluetooth is down");
    }

    private static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
