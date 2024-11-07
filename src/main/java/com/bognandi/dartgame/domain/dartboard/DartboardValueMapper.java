package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartgame.Dart;

import java.util.*;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class DartboardValueMapper {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DartboardValueMapper.class);

    public static Map<DartboardNotifiedValue,Dart> DARTVALUE_MAP = new LinkedHashMap<>();
    public static Map<String, DartboardNotifiedValue> FIELD_DART_MAP = new LinkedHashMap<>();
    private static Queue<DartboardNotifiedValue> SYNC_QUEUE = new ArrayDeque<>(Arrays.asList(DartboardNotifiedValue.values()));

    public static void pushValueToNotifiedValue(byte[] value) {
        if (SYNC_QUEUE.isEmpty()) {
            LOG.warn("No more values. Discarding value {}", new String(value));
            return;
        }

        String str = Base64.getEncoder().encodeToString(value);
        FIELD_DART_MAP.put(str, SYNC_QUEUE.remove());

        LOG.info("Next value to map: {}", SYNC_QUEUE.peek());
    }

    public static void print() {
        FIELD_DART_MAP.entrySet().forEach(entry ->
                System.out.println(String.format("MAP.put(\"%s\", %s);", entry.getKey(), entry.getValue())));
    }

    public static String bytesToHex(byte[] value) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<value.length; i++) {
            sb.append(String.format("%02X", value[i]));
        }
        return sb.toString();
    }

    public static DartboardNotifiedValue bytesToDartboardNotifiedValue(byte[] value) {
        String hexString = bytesToHex(value);
        return FIELD_DART_MAP.get(hexString);
    }

    public static Dart bytesToDart(byte[] value) {
        DartboardNotifiedValue dartboardNotifiedValue = bytesToDartboardNotifiedValue(value);
        return VALUE_MAP.get(dartboardNotifiedValue);
    }

    public static boolean isDartValue(DartboardNotifiedValue dartboardNotifiedValue) {
        return !Arrays.asList(DartboardNotifiedValue.INITIAL_CODE, DartboardNotifiedValue.RED_BUTTON).contains(dartboardNotifiedValue);
    }

}
