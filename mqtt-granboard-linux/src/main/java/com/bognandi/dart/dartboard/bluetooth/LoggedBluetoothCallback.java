package com.bognandi.dart.dartboard.bluetooth;

import com.welie.blessed.BluetoothCentralManagerCallback;
import com.welie.blessed.BluetoothCommandStatus;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ScanResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedBluetoothCallback extends BluetoothCentralManagerCallback {

    private static final Logger LOG = LoggerFactory.getLogger(LoggedBluetoothCallback.class);

    @Override
    public void onConnectedPeripheral(@NotNull BluetoothPeripheral peripheral) {
        LOG.trace("onConnectedPeripheral: peripheral={}", peripheral.getName());
        super.onConnectedPeripheral(peripheral);
    }

    @Override
    public void onConnectionFailed(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothCommandStatus status) {
        LOG.trace("onConnectionFailed: peripheral={}, status={}", peripheral.getName(), status);
        super.onConnectionFailed(peripheral, status);
    }

    @Override
    public void onDisconnectedPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothCommandStatus status) {
        LOG.trace("onDisconnectedPeripheral: peripheral={}, status={}", peripheral.getName(), status);
        super.onDisconnectedPeripheral(peripheral, status);
    }

    @Override
    public void onDiscoveredPeripheral(@NotNull BluetoothPeripheral peripheral, @NotNull ScanResult scanResult) {
        LOG.trace("onDiscoveredPeripheral: peripheral={}, scanResult={}", peripheral.getName(), scanResult);
        super.onDiscoveredPeripheral(peripheral, scanResult);
    }

    @Override
    public void onScanStarted() {
        LOG.trace("onScanStarted");
        super.onScanStarted();
    }

    @Override
    public void onScanStopped() {
        LOG.trace("onScanStopped");
        super.onScanStopped();
    }

    @Override
    public void onScanFailed(int errorCode) {
        LOG.warn("onScanFailed: errorCode={}", errorCode);
        super.onScanFailed(errorCode);
    }

    @Override
    public @NotNull String onPinRequest(@NotNull BluetoothPeripheral peripheral) {
        LOG.trace("onPinRequest: peripheral={}", peripheral.getName());
        return super.onPinRequest(peripheral);
    }
}
