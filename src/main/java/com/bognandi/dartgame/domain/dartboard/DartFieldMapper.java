package com.bognandi.dartgame.domain.dartboard;

import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class DartFieldMapper {
    public static Map<String, NotifiedValues> FIELD_DART_MAP = new LinkedHashMap<>();
    private static Queue<NotifiedValues> NOTIFIED_VALUES = new ArrayDeque<>(Arrays.asList(NotifiedValues.values()));
    private static NotifiedValues CURRENT_NOTIFIED_VALUE = null;
    private static Queue<NotifiedValues> SYNC_QUEUE = new SynchronousQueue<>();

    public static NotifiedValues nextNotifiedValue() {
        if (NOTIFIED_VALUES.isEmpty()) {
            return null;
        }
        return CURRENT_NOTIFIED_VALUE = NOTIFIED_VALUES.remove();
    }

    public static void pushValueToNotifiedValue(byte[] value) {
        SYNC_QUEUE.offer()

        String str = Base64.getEncoder().encodeToString(value);
        FIELD_DART_MAP.put(str, CURRENT_NOTIFIED_VALUE);
    }

    public static void print() {
        FIELD_DART_MAP.entrySet().forEach(entry ->
                System.out.println(String.format("MAP.put(\"%s\", %s);", entry.getKey(), entry.getValue())));
    }
}
