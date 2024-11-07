package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartgame.Dart;

import java.util.*;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class DartboardValueMapper {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DartboardValueMapper.class);

    public static Map<DartboardNotifiedValue,Dart> DARTVALUE_MAP = new LinkedHashMap<>();
    public static Map<String, DartboardNotifiedValue> FIELD_DART_MAP = createDartboardValueMapping();
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
            if (i>0) sb.append(" ");
            sb.append(String.format("%02X", value[i]));
        }
        return sb.toString();
    }

    public static DartboardNotifiedValue bytesToDartboardNotifiedValue(byte[] value) {
        String hexString = bytesToHex(value);
        return FIELD_DART_MAP.get(hexString);
    }

    private static Map<String,DartboardNotifiedValue> createDartboardValueMapping() {
        Map<String,DartboardNotifiedValue> map = new LinkedHashMap<>();

        map.put("", DartboardNotifiedValue.INITIAL_CODE);
/*

        \[((\w\w )*\w\w)\]->(\w*)
        [47 42 35 3B 31 30 31]->INITIAL_CODE
                [42 54 4E 40]->RED_BUTTON
                [32 2E 33 40]->ONE_INNER
                [39 2E 31 40]->TWO_INNER
                [37 2E 31 40]->THREE_INNER
                [30 2E 31 40]->FOUR_INNER
                [35 2E 31 40]->FIVE_INNER
                [31 2E 30 40]->SIX_INNER
                [36 2E 31 40]->SEVEN_INNER
                [36 2E 32 40]->EIGHT_INNER
                [39 2E 33 40]->NINE_INNER
                [32 2E 30 40]->TEN_INNER
                [37 2E 33 40]->ELEVEN_INNER
                [35 2E 30 40]->TWELVE_INNER
                [30 2E 30 40]->THIRTEEN_INNER
                [31 30 2E 33 40]->FOURTEEN_INNER
                [33 2E 30 40]->FIFTEEN_INNER
                [31 31 2E 30 40]->SIXTEEN_INNER
                [31 30 2E 31 40]->SEVENTEEN_INNER
                [31 2E 32 40]->EIGHTEEN_INNER
                [36 2E 31 40]->NINETEEN_INNER
                [33 2E 33 40]->TWENTY_INNER
                [32 2E 35 40]->ONE_OUTER
                [39 2E 32 40]->TWO_OUTER
                [37 2E 32 40]->THREE_OUTER
                [30 2E 35 40]->FOUR_OUTER
                [35 2E 34 40]->FIVE_OUTER
                [31 2E 33 40]->SIX_OUTER
                [31 31 2E 34 40]->SEVEN_OUTER
                [36 2E 35 40]->EIGHT_OUTER
                [39 2E 35 40]->NINE_OUTER
                [32 2E 32 40]->TEN_OUTER
                [37 2E 35 40]->ELEVEN_OUTER
                [35 2E 35 40]->TWELVE_OUTER
                [30 2E 34 40]->THIRTEEN_OUTER
                [31 30 2E 35 40]->FOURTEEN_OUTER
                [33 2E 32 40]->FIFTEEN_OUTER
                [31 31 2E 35 40]->SIXTEEN_OUTER
                [31 30 2E 32 40]->SEVENTEEN_OUTER
                [31 2E 35 40]->EIGHTEEN_OUTER
                [36 2E 33 40]->NINETEEN_OUTER
                [33 2E 35 40]->TWENTY_OUTER
                [38 2E 30 40]->BULLSEYE
                [32 2E 36 40]->DOUBLE_ONE
                [38 2E 32 40]->DOUBLE_TWO
                [38 2E 34 40]->DOUBLE_THREE
                [30 2E 36 40]->DOUBLE_FOUR
                [34 2E 36 40]->DOUBLE_FIVE
                [34 2E 34 40]->DOUBLE_SIX
                [38 2E 36 40]->DOUBLE_SEVEN
                [36 2E 36 40]->DOUBLE_EIGHT
                [39 2E 36 40]->DOUBLE_NINE
                [34 2E 33 40]->DOUBLE_TEN
                [37 2E 36 40]->DOUBLE_ELEVEN
                [35 2E 36 40]->DOUBLE_TWELVE
                [34 2E 35 40]->DOUBLE_THIRTEEN
                [31 30 2E 36 40]->DOUBLE_FOURTEEN
                [34 2E 32 40]->DOUBLE_FIFTEEN
                [31 31 2E 36 40]->DOUBLE_SIXTEEN
                [38 2E 33 40]->DOUBLE_SEVENTEEN
                [31 2E 36 40]->DOUBLE_EIGHTEEN
                [38 2E 35 40]->DOUBLE_NINETEEN
                [33 2E 36 40]->DOUBLE_TWENTY
                [34 2E 30 40]->DOUBLE_BULLSEYE
                [32 2E 34 40]->TRIPLE_ONE
                [39 2E 30 40]->TRIPLE_TWO
                [37 2E 30 40]->TRIPLE_THREE
                [30 2E 33 40]->TRIPLE_FOUR
                [35 2E 32 40]->TRIPLE_FIVE
                [31 2E 31 40]->TRIPLE_SIX
                [31 31 2E 32 40]->TRIPLE_SEVEN
                [36 2E 34 40]->TRIPLE_EIGHT
                [39 2E 34 40]->TRIPLE_NINE
                [32 2E 31 40]->TRIPLE_TEN
                [37 2E 34 40]->TRIPLE_ELEVEN
                [35 2E 33 40]->TRIPLE_TWELVE
                [30 2E 32 40]->TRIPLE_THIRTEEN
                [31 30 2E 34 40]->TRIPLE_FOURTEEN
                [33 2E 31 40]->TRIPLE_FIFTEEN
                [31 31 2E 33 40]->TRIPLE_SIXTEEN
                [31 30 2E 30 40]->TRIPLE_SEVENTEEN
                [31 2E 34 40]->TRIPLE_EIGHTEEN
                [36 2E 30 40]->TRIPLE_NINETEEN
                [33 2E 34 40]->TRIPLE_TWENTY
*/


        return Collections.unmodifiableMap(map);
    }
}
