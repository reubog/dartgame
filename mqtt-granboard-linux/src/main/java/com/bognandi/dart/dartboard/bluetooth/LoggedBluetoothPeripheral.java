package com.bognandi.dart.dartboard.bluetooth;

import com.welie.blessed.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class LoggedBluetoothPeripheral extends BluetoothPeripheralCallback {

    private static Logger LOG = LoggerFactory.getLogger(LoggedBluetoothPeripheral.class);

    @Override
    public void onServicesDiscovered(@NotNull BluetoothPeripheral peripheral, @NotNull List<BluetoothGattService> services) {
        String servicesStr = services.stream()
                .map(service -> service.getUuid().toString())
                .collect(Collectors.joining(", ", "[", "]"));

        LOG.debug("Services Discovered: Peripheral={}, Services={}", peripheral.getName(), servicesStr);
    }

    @Override
    public void onNotificationStateUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        LOG.debug("notificationStateUpdated: peripheral={}, cstic={}, status={}", peripheral.getName(), characteristic.getUuid(), status);
    }

    @Override
    public void onCharacteristicUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        StringBuffer sb = new StringBuffer();
        for(byte val: value) {
            sb.append(String.format("%02X ", val));
        }
        LOG.debug("onCharacteristicUpdate: peripheral={}, cstic={}, status={}, valueStr={} valueHex={}", peripheral.getName(), characteristic.getUuid(), status, new String(value), sb);
    }

    @Override
    public void onCharacteristicWrite(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        LOG.debug("onCharacteristicWrite: peripheral={}, cstic={}, status={}, value={}", peripheral.getName(), characteristic.getUuid(), status, new String(value));
    }

    @Override
    public void onDescriptorRead(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattDescriptor descriptor, @NotNull BluetoothCommandStatus status) {
        LOG.debug("onDescriptorRead: peripheral={}, descriptor={}, status={}, value={}", peripheral.getName(), descriptor.getUuid(), status, new String(value));

    }

    @Override
    public void onDescriptorWrite(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattDescriptor descriptor, @NotNull BluetoothCommandStatus status) {
        LOG.debug("onDescriptorWrite: peripheral={}, descriptor={}, status={}, value={}", peripheral.getName(), descriptor.getUuid(), status, new String(value));

    }

    @Override
    public void onBondingStarted(@NotNull BluetoothPeripheral peripheral) {
        LOG.debug("onBondingStarted: peripheral={}", peripheral.getName());

    }

    @Override
    public void onBondingSucceeded(@NotNull BluetoothPeripheral peripheral) {
        LOG.debug("onBondingSucceeded: peripheral={}", peripheral.getName());

    }

    @Override
    public void onBondingFailed(@NotNull BluetoothPeripheral peripheral) {
        LOG.debug("onBondingFailed: peripheral={}", peripheral.getName());

    }

    @Override
    public void onBondLost(@NotNull BluetoothPeripheral peripheral) {
        LOG.debug("onBondLost: peripheral={}", peripheral.getName());

    }

    @Override
    public void onReadRemoteRssi(@NotNull BluetoothPeripheral peripheral, int rssi, @NotNull BluetoothCommandStatus status) {
        LOG.debug("onReadRemoteRssi: peripheral={}, rssi={}, status={}", peripheral.getName(), rssi, status);
    }

}
