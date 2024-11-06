package com.bognandi.dartgame.app.service;

import com.bognandi.dartgame.domain.dartboard.BluetoothHandler2;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BluetoothDartboardService {

    private static final Logger LOG = LoggerFactory.getLogger(BluetoothDartboardService.class);

    private BluetoothHandler2 bluetoothHandler2;

    public BluetoothDartboardService() {
        LOG.info("Constructing...");
//        bluetoothHandler2 = new BluetoothHandler2();
//        bluetoothHandler2.start();
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Destroying!");
    }

}
