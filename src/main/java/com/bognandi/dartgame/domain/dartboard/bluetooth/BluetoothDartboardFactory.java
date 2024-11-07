package com.bognandi.dartgame.domain.dartboard.bluetooth;

import com.bognandi.dartgame.domain.dartboard.BluetoothDartboardPeripheral;
import com.bognandi.dartgame.domain.dartboard.DartboardFactory;
import com.bognandi.dartgame.domain.dartgame.DartBoard;
import com.welie.blessed.BluetoothCentralManager;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ScanResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BluetoothDartboardFactory implements DartboardFactory {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(BluetoothDartboardFactory.class);

    private final BluetoothCentralManager centralManager;
    private final LoggedBluetoothCentralManagerCallback centralManagerCallback = new LoggedBluetoothCentralManagerCallback(){
        @Override
        public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
            super.onDiscoveredPeripheral(peripheral, scanResult);

            if (dartboardName.equals(peripheral.getName())) {
                centralManager.stopScan();

                LOG.debug("Dartboard '{}' found! Connecting...", dartboardName);
                centralManager.connectPeripheral(peripheral, new BluetoothDartboardPeripheral());
            }
        }
    };
    private final String dartboardName;

    public BluetoothDartboardFactory(String dartboardName) {
        LOG.info("Starting BluetoothDartboardFactory");
        this.dartboardName = dartboardName;
        this.centralManager = new BluetoothCentralManager(centralManagerCallback);
        centralManagerCallback.setCentralManager(centralManager);
        centralManager.setRssiThreshold(-120);
    }

    @Override
    public void createDartboard(Consumer<DartBoard> dartBoardConsumer) {
        LOG.info("Scanning for dartboard: {}", dartboardName);
        centralManagerCallback.setDartBoardConsumer(dartBoardConsumer);
        centralManager.scanForPeripheralsWithNames(new String[]{dartboardName});
    }

    @Override
    public void destroyDartboard(DartBoard dartBoard) {

    }

    public void shutdown() {
        LOG.info("Shutting down BluetoothDartboardFactory");
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
        LOG.debug("Bluetooth is down");
    }

    private static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
