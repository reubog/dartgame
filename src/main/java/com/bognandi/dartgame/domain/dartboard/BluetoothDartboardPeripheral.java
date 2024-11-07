package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartboard.bluetooth.LoggedBluetoothPeripheral;
import com.bognandi.dartgame.domain.dartgame.Dart;
import com.bognandi.dartgame.domain.dartgame.Dartboard;
import com.bognandi.dartgame.domain.dartgame.DartBoardEventListener;
import com.welie.blessed.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BluetoothDartboardPeripheral extends LoggedBluetoothPeripheral implements Dartboard {

    private static Logger LOG = LoggerFactory.getLogger(BluetoothDartboardPeripheral.class);

    private Consumer<Dartboard> dartBoardConsumer;
    private String dartboardName;
    private final List<DartBoardEventListener> listeners = new ArrayList<>();

    public BluetoothDartboardPeripheral(String dartboardName, Consumer<Dartboard> dartBoardConsumer) {
        this.dartBoardConsumer = dartBoardConsumer;
        this.dartboardName = dartboardName;
    }

    @Override
    public void addEventListener(DartBoardEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(DartBoardEventListener listener) {
        listeners.remove(listener);
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

        dartBoardConsumer.accept(this);
    }

    @Override
    public void onCharacteristicUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onCharacteristicUpdate(peripheral, value, characteristic, status);

        DartboardNotifiedValue dartboardNotifiedValue = DartboardValueMapper.bytesToDartboardNotifiedValue(value);

        if (dartboardNotifiedValue.isButton()) {
            LOG.info("Button pressed, notifying listeners");
            listeners.forEach(listener -> listener.onButton());
        } else if (dartboardNotifiedValue.isDartValue()) {
            Dart dart = DartboardValueMapper.DART_MAP.get(dartboardNotifiedValue);
            LOG.info("Dart thrown, notifying listeners: {}", dart);
            listeners.forEach(listener -> listener.onDartThrown(dart));
        } else {
            LOG.info("Unknown value: {}", dartboardNotifiedValue);
        }

    }

}
