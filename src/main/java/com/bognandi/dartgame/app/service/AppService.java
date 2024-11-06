package com.bognandi.dartgame.app.service;

import com.bognandi.dartgame.domain.dartgame.DartBoardEventListener;
import com.welie.blessed.BluetoothCentralManager;
import com.welie.blessed.BluetoothPeripheral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppService {

    private static Logger LOG = LoggerFactory.getLogger(AppService.class);

    private BluetoothCentralManager central;
    private BluetoothPeripheral peripheral;

    public void connectDartboard(DartBoardEventListener listener) {

    }

}
