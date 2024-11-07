package com.bognandi.dartgame.app.service;

import com.bognandi.dartgame.domain.dartboard.bluetooth.LoggedBluetoothCentralManagerCallback;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BluetoothDartboardService {

    private static final Logger LOG = LoggerFactory.getLogger(BluetoothDartboardService.class);

    private String dartboardName;

    private LoggedBluetoothCentralManagerCallback loggedBluetoothCentralManagerCallback;

    public BluetoothDartboardService(@Value("${bluetooth.dartboard.name}") String dartboardName) {
        LOG.info("Constructing service for dartboard named '{}'", dartboardName);
        this.dartboardName = dartboardName;
//        bluetoothHandler2 = new BluetoothHandler2();
//        bluetoothHandler2.start();
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Destroying!");
    }

}
