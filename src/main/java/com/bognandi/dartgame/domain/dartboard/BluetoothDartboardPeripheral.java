package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartboard.bluetooth.LoggedBluetoothPeripheral;
import com.bognandi.dartgame.domain.dartgame.Dart;
import com.bognandi.dartgame.domain.dartgame.DartBoard;
import com.bognandi.dartgame.domain.dartgame.DartBoardEventListener;
import com.welie.blessed.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BluetoothDartboardPeripheral extends LoggedBluetoothPeripheral implements DartBoard {

    private static Logger LOG = LoggerFactory.getLogger(BluetoothDartboardPeripheral.class);

    private final List<DartBoardEventListener> listeners = new ArrayList<>();

    @Override
    public void addEventListener(DartBoardEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(DartBoardEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onCharacteristicUpdate(@NotNull BluetoothPeripheral peripheral, @NotNull byte[] value, @NotNull BluetoothGattCharacteristic characteristic, @NotNull BluetoothCommandStatus status) {
        super.onCharacteristicUpdate(peripheral,value,characteristic,status);

        DartboardNotifiedValue dartboardNotifiedValue = DartboardValueMapper.bytesToDartboardNotifiedValue(value);

        if (dartboardNotifiedValue.isButton()) {
            LOG.info("Button pressed, notifying listeners");
            listeners.forEach(listener -> listener.onButton());
        }
        else if (dartboardNotifiedValue.isDartValue()) {
            Dart dart = DartboardValueMapper.DARTVALUE_MAP.get(dartboardNotifiedValue);
            LOG.info("Dart thrown, notifying listeners: {}", dart);
            listeners.forEach(listener -> listener.onDartThrown(dart));
        }
        else {
            LOG.info("Unknown value: {}", dartboardNotifiedValue);
        }

    }

}
