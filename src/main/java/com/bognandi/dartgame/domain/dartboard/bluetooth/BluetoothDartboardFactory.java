package com.bognandi.dartgame.domain.dartboard.bluetooth;

import com.bognandi.dartgame.domain.dartboard.BluetoothDartboardPeripheral;
import com.bognandi.dartgame.domain.dartboard.DartboardFactory;
import com.bognandi.dartgame.domain.dartgame.Dartboard;
import com.welie.blessed.BluetoothCentralManager;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ScanResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BluetoothDartboardFactory implements DartboardFactory {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(BluetoothDartboardFactory.class);

    private final BluetoothCentralManager centralManager;
    private final Callback centralManagerCallback = new Callback();

    public BluetoothDartboardFactory() {
        LOG.info("Starting BluetoothDartboardFactory");
        this.centralManager = new BluetoothCentralManager(centralManagerCallback);
        centralManagerCallback.setCentralManager(centralManager);
        centralManager.setRssiThreshold(-120);
    }

    @Override
    public void createDartboard(String name, Consumer<Dartboard> dartBoardConsumer) {
        LOG.info("Scanning for dartboard: {}", name);
        centralManagerCallback.setDartBoardConsumer(name, dartBoardConsumer);
        centralManager.scanForPeripheralsWithNames(new String[]{name});
    }

    @Override
    public void destroyDartboard(Dartboard dartBoard) {

    }

    @Override
    public void shutdown() {
        LOG.info("Shutting down BluetoothDartboardFactory");
        // shutting down
        centralManager.stopScan();

        centralManager.getConnectedPeripherals().forEach(peripheral -> {
            peripheral.getNotifyingCharacteristics().forEach(cstic -> {
                peripheral.setNotify(cstic.getService().getUuid(), cstic.getUuid(), false);
            });
            waitSeconds(1);
            peripheral.cancelConnection();
        });
        waitSeconds(5);
        centralManager.shutdown();
        LOG.debug("Bluetooth is down");
    }

    private static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Callback extends LoggedBluetoothCentralManagerCallback {
        private Consumer<Dartboard> dartBoardConsumer;
        private String dartboardName;

        public void setDartBoardConsumer(String dartboardName, Consumer<Dartboard> dartBoardConsumer) {
            this.dartboardName = dartboardName;
            this.dartBoardConsumer = dartBoardConsumer;
        }

        @Override
        public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
            super.onDiscoveredPeripheral(peripheral, scanResult);

            if (dartboardName.equals(peripheral.getName())) {
                centralManager.stopScan();

                LOG.debug("Dartboard '{}' found! Connecting...", dartboardName);
                centralManager.connectPeripheral(peripheral, new BluetoothDartboardPeripheral(dartboardName, dartBoardConsumer));
            }
        }

        @Override
        public void onConnectedPeripheral(@NotNull BluetoothPeripheral peripheral) {
            super.onConnectedPeripheral(peripheral);

            if (dartboardName.equals(peripheral.getName())) {
                LOG.debug("Dartboard '{}' connected!", dartboardName);
            }
        }
    }

}
