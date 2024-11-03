package com.bognandi.dartgame.domain.dartboard;

import tinyb.BluetoothManager;

public class BluetoothDartboardFactory {

    public static void main(String[] args) {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        manager.startNearbyDiscovery(device -> log("Device: " + device.getName()), 1000, true);
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
