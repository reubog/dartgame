package com.bognandi.dartgame.domain.dartboard;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class DartFieldMapper {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DartFieldMapper.class);

    public static Map<String, NotifiedValues> FIELD_DART_MAP = new LinkedHashMap<>();
    private static Queue<NotifiedValues> SYNC_QUEUE = new ArrayDeque<>(Arrays.asList(NotifiedValues.values()));

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
}
