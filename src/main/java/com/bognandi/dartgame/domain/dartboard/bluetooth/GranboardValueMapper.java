package com.bognandi.dartgame.domain.dartboard.bluetooth;

import com.bognandi.dartgame.domain.dartboard.DartboardNotifiedValue;
import com.bognandi.dartgame.domain.dartboard.DartboardValueMapper;
import com.welie.blessed.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class GranboardValueMapper {
    private static final Logger LOG = LoggerFactory.getLogger(GranboardValueMapper.class);

    private static final String BOARD_NAME = "GRANBOARD";

    public static void main(String[] args) throws InterruptedException {
        LOG.debug("Start");
        Callback callback = new Callback();
        BluetoothCentralManager centralManager = new BluetoothCentralManager(callback);
        callback.setCentralManager(centralManager);
        centralManager.setRssiThreshold(-120);
        centralManager.scanForPeripheralsWithNames(new String[]{BOARD_NAME});

        waitForUserPressReturn();

        // shutting down
        centralManager.getConnectedPeripherals().forEach(peripheral -> {
            peripheral.getNotifyingCharacteristics().forEach(cstic -> {
                peripheral.setNotify(cstic.getService().getUuid(), cstic.getUuid(), false);
            });
            waitSeconds(1);
            peripheral.cancelConnection();
        });
        waitSeconds(5);
        centralManager.stopScan();
        centralManager.shutdown();
        LOG.debug("End");
    }

    private static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void waitForUserPressReturn() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Press return to continue...");
        try {
            String s = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Callback extends LoggedBluetoothCentralManagerCallback {

        private BluetoothCentralManager centralManager;

        public void setCentralManager(BluetoothCentralManager centralManager) {
            this.centralManager = centralManager;
        }

        @Override
        public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
            super.onDiscoveredPeripheral(peripheral, scanResult);

            if (BOARD_NAME.equals(peripheral.getName())) {
                centralManager.stopScan();

                LOG.debug("Board found! Connecting...");
                centralManager.connectPeripheral(peripheral, new Granboard());

            }
        }
    }

    private static class Granboard extends LoggedBluetoothPeripheral {

        private Map<String, DartboardNotifiedValue> map = new LinkedHashMap<>();
        private Queue<DartboardNotifiedValue> queue = new ArrayDeque<>(Arrays.asList(DartboardNotifiedValue.values()));

        @Override
        public void onServicesDiscovered(@NotNull BluetoothPeripheral peripheral, @NotNull List<BluetoothGattService> services) {
            super.onServicesDiscovered(peripheral, services);

            services.forEach(service -> {
                service.getCharacteristics().stream()
                        .forEach(cstic -> {
                            if (cstic.supportsNotifying()) {
                                LOG.debug("Setting Notify ON on peripheral={}, service={}, cstic={}", peripheral.getName(), service.getUuid(), cstic.getUuid());
                                peripheral.setNotify(service.getUuid(), cstic.getUuid(), true);
                            } else if (cstic.supportsReading()) {
                                LOG.debug("Reading peripheral={}, service={}, cstic={}", peripheral.getName(), service.getUuid(), cstic.getUuid());
                                peripheral.readCharacteristic(service.getUuid(), cstic.getUuid());
                            }
                        });
            });
        }

        @Override
        public void onCharacteristicUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
            super.onCharacteristicUpdate(peripheral, value, characteristic, status);

            DartboardNotifiedValue nextValue = queue.remove();
            String hexStr = DartboardValueMapper.bytesToHex(value);
            map.put(hexStr, nextValue);
            LOG.info("Value added to map: [{}]->{}", hexStr, nextValue);

            if (queue.isEmpty()) {
                LOG.info("ALL DONE !!!");

                String str = map.entrySet().stream()
                        .map(entry -> String.format("map.put(\"%s\", %s);", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("\n"));

                LOG.info("All values:\n{}", str);
            } else {
                LOG.info("Next Value: {}", queue.peek());
            }
        }


    }
}
