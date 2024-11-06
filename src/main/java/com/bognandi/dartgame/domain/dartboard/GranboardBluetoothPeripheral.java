package com.bognandi.dartgame.domain.dartboard;

import com.welie.blessed.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GranboardBluetoothPeripheral extends BluetoothPeripheralCallback {
    public GranboardBluetoothPeripheral() {
        super();
    }

    @Override
    public void onServicesDiscovered(@NotNull BluetoothPeripheral peripheral, @NotNull List<BluetoothGattService> services) {
        super.onServicesDiscovered(peripheral, services);
    }

    @Override
    public void onNotificationStateUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onNotificationStateUpdate(peripheral, characteristic, status);
    }

    @Override
    public void onCharacteristicUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onCharacteristicUpdate(peripheral, value, characteristic, status);
    }

    @Override
    public void onCharacteristicWrite(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onCharacteristicWrite(peripheral, value, characteristic, status);
    }

    @Override
    public void onDescriptorRead(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattDescriptor descriptor, @NotNull BluetoothCommandStatus status) {
        super.onDescriptorRead(peripheral, value, descriptor, status);
    }

    @Override
    public void onDescriptorWrite(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattDescriptor descriptor, @NotNull BluetoothCommandStatus status) {
        super.onDescriptorWrite(peripheral, value, descriptor, status);
    }

    @Override
    public void onBondingStarted(@NotNull BluetoothPeripheral peripheral) {
        super.onBondingStarted(peripheral);
    }

    @Override
    public void onBondingSucceeded(@NotNull BluetoothPeripheral peripheral) {
        super.onBondingSucceeded(peripheral);
    }

    @Override
    public void onBondingFailed(@NotNull BluetoothPeripheral peripheral) {
        super.onBondingFailed(peripheral);
    }

    @Override
    public void onBondLost(@NotNull BluetoothPeripheral peripheral) {
        super.onBondLost(peripheral);
    }

    @Override
    public void onReadRemoteRssi(@NotNull BluetoothPeripheral peripheral, int rssi, @NotNull BluetoothCommandStatus status) {
        super.onReadRemoteRssi(peripheral, rssi, status);
    }
}
