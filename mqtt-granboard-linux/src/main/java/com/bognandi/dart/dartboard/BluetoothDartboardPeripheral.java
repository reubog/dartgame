package com.bognandi.dart.dartboard;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardValueMapper;
import com.bognandi.dart.dartboard.bluetooth.LoggedBluetoothPeripheral;
import com.bognandi.dart.dartboard.mqtt.GranboardMessage;
import com.bognandi.dart.dartboard.mqtt.GranboardMqttPublisher;
import com.welie.blessed.BluetoothCommandStatus;
import com.welie.blessed.BluetoothGattCharacteristic;
import com.welie.blessed.BluetoothGattService;
import com.welie.blessed.BluetoothPeripheral;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BluetoothDartboardPeripheral extends LoggedBluetoothPeripheral {

    private static Logger LOG = LoggerFactory.getLogger(BluetoothDartboardPeripheral.class);

    private GranboardMqttPublisher publisher;

    public BluetoothDartboardPeripheral(GranboardMqttPublisher publisher) {
        this.publisher = publisher;
    }

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

        publisher.publish(new GranboardMessage(DartboardStatus.CONNECTED, null));
    }

    @Override
    public void onCharacteristicUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onCharacteristicUpdate(peripheral, value, characteristic, status);
        DartboardValue dartboardValue = DartboardValueMapper.bytesToDartboardNotifiedValue(value);
        publisher.publish(new GranboardMessage(DartboardStatus.CONNECTED, dartboardValue));
    }

    @Override
    public void onNotificationStateUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onNotificationStateUpdate(peripheral, characteristic, status);
        if (BluetoothCommandStatus.NOT_CONNECTED ==  status) {
            publisher.publish(new GranboardMessage(DartboardStatus.DISCONNECTED, null));
        }
    }
}
