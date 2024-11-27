package com.bognandi.dart.dartboard.bluetooth.app;

import com.welie.blessed.*;
import jakarta.annotation.PreDestroy;
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
    private Callback centralManagerCallback;
    private Function<BluetoothPeripheral, BluetoothPeripheralCallback> callbackFactory;

    public BluetoothDartboardService(
            @Value("${dartboard.bluetooth.name}") String scanForName,
            @Autowired Function<BluetoothPeripheral, BluetoothPeripheralCallback> callbackFactory
    ) {
        LOG.info("Starting BluetoothDartboardFactory");
        this.centralManager = new BluetoothCentralManager(centralManagerCallback = new Callback(scanForName));
        centralManager.setRssiThreshold(-120);

        scan(scanForName);
    }

    public void scan(String name) {
        LOG.info("Scanning for device name={}", name);
        centralManager.scanForPeripheralsWithNames(new String[]{name});
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("Shutting down BluetoothDartboardFactory");

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

    private class Callback extends BluetoothCentralManagerCallback {

        private String peripheralName;

        public Callback(String peripheralName) {
            this.peripheralName = peripheralName;
        }

        @Override
        public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
            super.onDiscoveredPeripheral(peripheral, scanResult);

            if (peripheralName.equals(peripheral.getName())) {
                centralManager.stopScan();

                LOG.debug("Dartboard '{}' with address={} found! Connecting...", peripheralName, peripheral.getAddress());
                centralManager.autoConnectPeripheral(peripheral, callbackFactory.apply(peripheral));
            }
        }

        @Override
        public void onConnectedPeripheral(@NotNull BluetoothPeripheral peripheral) {
            super.onConnectedPeripheral(peripheral);

            if (peripheralName.equals(peripheral.getName())) {
                LOG.debug("Dartboard '{}' connected!", peripheralName);
            }
        }
    }

}
