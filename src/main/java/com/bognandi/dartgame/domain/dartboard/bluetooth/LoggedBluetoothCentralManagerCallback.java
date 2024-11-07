package com.bognandi.dartgame.domain.dartboard.bluetooth;

import com.bognandi.dartgame.domain.dartboard.BluetoothDartboardPeripheral;
import com.bognandi.dartgame.domain.dartboard.DartboardValueMapper;
import com.welie.blessed.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LoggedBluetoothCentralManagerCallback extends BluetoothCentralManagerCallback {

    private static Logger LOG = LoggerFactory.getLogger(LoggedBluetoothCentralManagerCallback.class);

    protected BluetoothCentralManager centralManager;

    public void setCentralManager(BluetoothCentralManager centralManager) {
        this.centralManager = centralManager;
    }

    @Override
    public void onConnectedPeripheral(@NotNull BluetoothPeripheral peripheral) {
        LOG.debug("Connected: Address={}, Name={}", peripheral.getAddress(), peripheral.getName());
    }

    @Override
    public void onConnectionFailed(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothCommandStatus status) {
        LOG.debug("Connection Failed: Address={}, Name={}, Status={}", peripheral.getAddress(), peripheral.getName(), status.getValue());
    }

    @Override
    public void onDisconnectedPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothCommandStatus status) {
        LOG.debug("Disconnected: Address={}, Name={}, Status={}", peripheral.getAddress(), peripheral.getName(), status.getValue());
    }

    @Override
    public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
        LOG.debug("Discovered Peripheral: Address={}, Name={}, Status=[{}]", peripheral.getAddress(), peripheral.getName(), String.join(",", scanResult.getUuids().stream().map(UUID::toString).collect(Collectors.joining(", "))));
    }

    @Override
    public void onScanStarted() {
        LOG.debug("Scan started");
    }

    @Override
    public void onScanStopped() {
        LOG.debug("Scan stopped");
    }

    @Override
    public void onScanFailed(int errorCode) {
        LOG.debug("Scan failed, error={}", errorCode);
    }

    @Override
    public @NotNull String onPinRequest(@NotNull BluetoothPeripheral peripheral) {
        LOG.debug("PIN Requested: Address={}, Name={}", peripheral.getAddress(), peripheral.getName());
        return "0000";
    }

    public static void main(String[] args) throws InterruptedException {
        LOG.debug("Start");
        LoggedBluetoothCentralManagerCallback callback = new LoggedBluetoothCentralManagerCallback(){
            @Override
            public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
                super.onDiscoveredPeripheral(peripheral, scanResult);

                if ("GRANBOARD".equals(peripheral.getName())) {
                    centralManager.stopScan();

                    LOG.debug("GRANBOARD found! Connecting...");
                    centralManager.connectPeripheral(peripheral, new BluetoothDartboardPeripheral());

                }
            }
        };
        BluetoothCentralManager centralManager = new BluetoothCentralManager(callback);
        callback.setCentralManager(centralManager);
        centralManager.setRssiThreshold(-120);
        centralManager.scanForPeripheralsWithNames(new String[]{"GRANBOARD"});

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
}
