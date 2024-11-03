package com.bognandi.dartgame.domain.dartboard;

//import tinyb.BluetoothException;
//import tinyb.BluetoothManager;

import com.welie.blessed.BluetoothCentralManager;
import com.welie.blessed.BluetoothCentralManagerCallback;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ScanResult;

import java.util.UUID;

public class BluetoothDartboardFactory {

//    public static void main(String[] args) throws BluetoothException, InterruptedException {
//        BluetoothManager manager = BluetoothManager.getBluetoothManager();
//        manager.startNearbyDiscovery(device -> log("Device: " + device.getName()), 1000, true);
//        Thread.sleep(10*1000);
//    }

    public static void main(String[] args) throws InterruptedException {

        BluetoothCentralManager central = new BluetoothCentralManager(new CAllback());
        central.scanForPeripheralsWithAddresses(new String[]{"EA:11:C6:FD:E9:57"});
        UUID BLOODPRESSURE_SERVICE_UUID = UUID.fromString("442f1571-8a00-9a28-cbe1-e1d4212d53eb");

       // central.scanForPeripheralsWithServices(new UUID[]{BLOODPRESSURE_SERVICE_UUID});

        //Thread.sleep(10*1000);

    }

    static class CAllback extends BluetoothCentralManagerCallback {

        @Override
        public void onDiscoveredPeripheral(BluetoothPeripheral peripheral, ScanResult scanResult) {
            log(String.format("Found: Name=%s, Address=%s",
                    peripheral.getName(),
                    peripheral.getAddress()));


//                central.stopScan();
            //              central.connectPeripheral(peripheral, peripheralCallback);
        }

    }

    private static void log(String message) {
        System.out.println(message);
    }
}
