package com.bognandi.dart.dartboard.bluetooth;

import com.welie.blessed.BluetoothCommandStatus;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ScanResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class BluetoothSearchCallback extends LoggedBluetoothCallback {

    private static final Logger LOG = LoggerFactory.getLogger(BluetoothSearchCallback.class);

    private String peripheralName;
    private Consumer<BluetoothPeripheral> onConnected;
    private Consumer<BluetoothPeripheral> onDisconnected;
    private Consumer<BluetoothPeripheral> onDiscovered;

    public void setPeripheralName(String peripheralName) {
        this.peripheralName = peripheralName;
    }

    public void setOnDiscovered(Consumer<BluetoothPeripheral> onDiscovered) {
        this.onDiscovered = onDiscovered;
    }

    public void setOnConnected(Consumer<BluetoothPeripheral> onConnected) {
        this.onConnected = onConnected;
    }

    public void setOnDisconnected(Consumer<BluetoothPeripheral> onDisconnected) {
        this.onDisconnected = onDisconnected;
    }

    @Override
    public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
        super.onDiscoveredPeripheral(peripheral, scanResult);

        if (peripheralName.equals(peripheral.getName())) {
            LOG.debug("Dartboard '{}' with address={} found! Connecting...", peripheralName, peripheral.getAddress());
            onDiscovered.accept(peripheral);
        }
    }

    @Override
    public void onConnectedPeripheral(@NotNull BluetoothPeripheral peripheral) {
        super.onConnectedPeripheral(peripheral);

        if (peripheralName.equals(peripheral.getName())) {
            LOG.debug("Dartboard '{}' connected!", peripheralName);
            onConnected.accept(peripheral);
        }
    }

    @Override
    public void onDisconnectedPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothCommandStatus status) {
        super.onDisconnectedPeripheral(peripheral, status);

        if (peripheralName.equals(peripheral.getName())) {
            LOG.debug("Dartboard '{}' disconnected!", peripheralName);
            onDisconnected.accept(peripheral);
        }
    }
}
