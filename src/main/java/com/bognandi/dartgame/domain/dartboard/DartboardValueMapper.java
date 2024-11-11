package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartgame.Dart;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DartboardValueMapper {

    public static Map<DartboardNotifiedValue,Dart> DART_MAP = createDartMapping();
    public static Map<String, DartboardNotifiedValue> BOARD_VALUE_MAP = createDartboardValueMapping();

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
        return BOARD_VALUE_MAP.get(hexString);
    }

    private static Map<String,DartboardNotifiedValue> createDartboardValueMapping() {
        Map<String,DartboardNotifiedValue> map = new LinkedHashMap<>();
        map.put("47 42 35 3B 31 30 31", DartboardNotifiedValue.INITIAL_CODE);

        map.put("42 54 4E 40", DartboardNotifiedValue.RED_BUTTON);
        map.put("32 2E 33 40", DartboardNotifiedValue.ONE_INNER);
        map.put("39 2E 31 40", DartboardNotifiedValue.TWO_INNER);
        map.put("37 2E 31 40", DartboardNotifiedValue.THREE_INNER);
        map.put("30 2E 31 40", DartboardNotifiedValue.FOUR_INNER);
        map.put("35 2E 31 40", DartboardNotifiedValue.FIVE_INNER);
        map.put("31 2E 30 40", DartboardNotifiedValue.SIX_INNER);
        map.put("31 31 2E 31 40", DartboardNotifiedValue.SEVEN_INNER);
        map.put("36 2E 32 40", DartboardNotifiedValue.EIGHT_INNER);
        map.put("39 2E 33 40", DartboardNotifiedValue.NINE_INNER);
        map.put("32 2E 30 40", DartboardNotifiedValue.TEN_INNER);
        map.put("37 2E 33 40", DartboardNotifiedValue.ELEVEN_INNER);
        map.put("35 2E 30 40", DartboardNotifiedValue.TWELVE_INNER);
        map.put("30 2E 30 40", DartboardNotifiedValue.THIRTEEN_INNER);
        map.put("31 30 2E 33 40", DartboardNotifiedValue.FOURTEEN_INNER);
        map.put("33 2E 30 40", DartboardNotifiedValue.FIFTEEN_INNER);
        map.put("31 31 2E 30 40", DartboardNotifiedValue.SIXTEEN_INNER);
        map.put("31 30 2E 31 40", DartboardNotifiedValue.SEVENTEEN_INNER);
        map.put("31 2E 32 40", DartboardNotifiedValue.EIGHTEEN_INNER);
        map.put("36 2E 31 40", DartboardNotifiedValue.NINETEEN_INNER);
        map.put("33 2E 33 40", DartboardNotifiedValue.TWENTY_INNER);

        map.put("32 2E 35 40", DartboardNotifiedValue.ONE_OUTER);
        map.put("39 2E 32 40", DartboardNotifiedValue.TWO_OUTER);
        map.put("37 2E 32 40", DartboardNotifiedValue.THREE_OUTER);
        map.put("30 2E 35 40", DartboardNotifiedValue.FOUR_OUTER);
        map.put("35 2E 34 40", DartboardNotifiedValue.FIVE_OUTER);
        map.put("31 2E 33 40", DartboardNotifiedValue.SIX_OUTER);
        map.put("31 31 2E 34 40", DartboardNotifiedValue.SEVEN_OUTER);
        map.put("36 2E 35 40", DartboardNotifiedValue.EIGHT_OUTER);
        map.put("39 2E 35 40", DartboardNotifiedValue.NINE_OUTER);
        map.put("32 2E 32 40", DartboardNotifiedValue.TEN_OUTER);
        map.put("37 2E 35 40", DartboardNotifiedValue.ELEVEN_OUTER);
        map.put("35 2E 35 40", DartboardNotifiedValue.TWELVE_OUTER);
        map.put("30 2E 34 40", DartboardNotifiedValue.THIRTEEN_OUTER);
        map.put("31 30 2E 35 40", DartboardNotifiedValue.FOURTEEN_OUTER);
        map.put("33 2E 32 40", DartboardNotifiedValue.FIFTEEN_OUTER);
        map.put("31 31 2E 35 40", DartboardNotifiedValue.SIXTEEN_OUTER);
        map.put("31 30 2E 32 40", DartboardNotifiedValue.SEVENTEEN_OUTER);
        map.put("31 2E 35 40", DartboardNotifiedValue.EIGHTEEN_OUTER);
        map.put("36 2E 33 40", DartboardNotifiedValue.NINETEEN_OUTER);
        map.put("33 2E 35 40", DartboardNotifiedValue.TWENTY_OUTER);

        map.put("38 2E 30 40", DartboardNotifiedValue.BULLSEYE);

        map.put("32 2E 36 40", DartboardNotifiedValue.DOUBLE_ONE);
        map.put("38 2E 32 40", DartboardNotifiedValue.DOUBLE_TWO);
        map.put("38 2E 34 40", DartboardNotifiedValue.DOUBLE_THREE);
        map.put("30 2E 36 40", DartboardNotifiedValue.DOUBLE_FOUR);
        map.put("34 2E 36 40", DartboardNotifiedValue.DOUBLE_FIVE);
        map.put("34 2E 34 40", DartboardNotifiedValue.DOUBLE_SIX);
        map.put("38 2E 36 40", DartboardNotifiedValue.DOUBLE_SEVEN);
        map.put("36 2E 36 40", DartboardNotifiedValue.DOUBLE_EIGHT);
        map.put("39 2E 36 40", DartboardNotifiedValue.DOUBLE_NINE);
        map.put("34 2E 33 40", DartboardNotifiedValue.DOUBLE_TEN);
        map.put("37 2E 36 40", DartboardNotifiedValue.DOUBLE_ELEVEN);
        map.put("35 2E 36 40", DartboardNotifiedValue.DOUBLE_TWELVE);
        map.put("34 2E 35 40", DartboardNotifiedValue.DOUBLE_THIRTEEN);
        map.put("31 30 2E 36 40", DartboardNotifiedValue.DOUBLE_FOURTEEN);
        map.put("34 2E 32 40", DartboardNotifiedValue.DOUBLE_FIFTEEN);
        map.put("31 31 2E 36 40", DartboardNotifiedValue.DOUBLE_SIXTEEN);
        map.put("38 2E 33 40", DartboardNotifiedValue.DOUBLE_SEVENTEEN);
        map.put("31 2E 36 40", DartboardNotifiedValue.DOUBLE_EIGHTEEN);
        map.put("38 2E 35 40", DartboardNotifiedValue.DOUBLE_NINETEEN);
        map.put("33 2E 36 40", DartboardNotifiedValue.DOUBLE_TWENTY);

        map.put("34 2E 30 40", DartboardNotifiedValue.DOUBLE_BULLSEYE);

        map.put("32 2E 34 40", DartboardNotifiedValue.TRIPLE_ONE);
        map.put("39 2E 30 40", DartboardNotifiedValue.TRIPLE_TWO);
        map.put("37 2E 30 40", DartboardNotifiedValue.TRIPLE_THREE);
        map.put("30 2E 33 40", DartboardNotifiedValue.TRIPLE_FOUR);
        map.put("35 2E 32 40", DartboardNotifiedValue.TRIPLE_FIVE);
        map.put("31 2E 31 40", DartboardNotifiedValue.TRIPLE_SIX);
        map.put("31 31 2E 32 40", DartboardNotifiedValue.TRIPLE_SEVEN);
        map.put("36 2E 34 40", DartboardNotifiedValue.TRIPLE_EIGHT);
        map.put("39 2E 34 40", DartboardNotifiedValue.TRIPLE_NINE);
        map.put("32 2E 31 40", DartboardNotifiedValue.TRIPLE_TEN);
        map.put("37 2E 34 40", DartboardNotifiedValue.TRIPLE_ELEVEN);
        map.put("35 2E 33 40", DartboardNotifiedValue.TRIPLE_TWELVE);
        map.put("30 2E 32 40", DartboardNotifiedValue.TRIPLE_THIRTEEN);
        map.put("31 30 2E 34 40", DartboardNotifiedValue.TRIPLE_FOURTEEN);
        map.put("33 2E 31 40", DartboardNotifiedValue.TRIPLE_FIFTEEN);
        map.put("31 31 2E 33 40", DartboardNotifiedValue.TRIPLE_SIXTEEN);
        map.put("31 30 2E 30 40", DartboardNotifiedValue.TRIPLE_SEVENTEEN);
        map.put("31 2E 34 40", DartboardNotifiedValue.TRIPLE_EIGHTEEN);
        map.put("36 2E 30 40", DartboardNotifiedValue.TRIPLE_NINETEEN);
        map.put("33 2E 34 40", DartboardNotifiedValue.TRIPLE_TWENTY);

        return Collections.unmodifiableMap(map);
    }

    private static Map<DartboardNotifiedValue, Dart> createDartMapping() {
        Map<DartboardNotifiedValue,Dart> map = new LinkedHashMap<>();

        map.put(DartboardNotifiedValue.ONE_INNER, Dart.ONE);
        map.put(DartboardNotifiedValue.TWO_INNER, Dart.TWO);
        map.put(DartboardNotifiedValue.THREE_INNER, Dart.THREE);
        map.put(DartboardNotifiedValue.FOUR_INNER, Dart.FOUR);
        map.put(DartboardNotifiedValue.FIVE_INNER, Dart.FIVE);
        map.put(DartboardNotifiedValue.SIX_INNER, Dart.SIX);
        map.put(DartboardNotifiedValue.SEVEN_INNER, Dart.SEVEN);
        map.put(DartboardNotifiedValue.EIGHT_INNER, Dart.EIGHT);
        map.put(DartboardNotifiedValue.NINE_INNER, Dart.NINE);
        map.put(DartboardNotifiedValue.TEN_INNER, Dart.TEN);
        map.put(DartboardNotifiedValue.ELEVEN_INNER, Dart.ELEVEN);
        map.put(DartboardNotifiedValue.TWELVE_INNER, Dart.TWELVE);
        map.put(DartboardNotifiedValue.THIRTEEN_INNER, Dart.THIRTEEN);
        map.put(DartboardNotifiedValue.FOURTEEN_INNER, Dart.FOURTEEN);
        map.put(DartboardNotifiedValue.FIFTEEN_INNER, Dart.FIFTEEN);
        map.put(DartboardNotifiedValue.SIXTEEN_INNER, Dart.SIXTEEN);
        map.put(DartboardNotifiedValue.SEVENTEEN_INNER, Dart.SEVENTEEN);
        map.put(DartboardNotifiedValue.EIGHTEEN_INNER, Dart.EIGHTEEN);
        map.put(DartboardNotifiedValue.NINETEEN_INNER, Dart.NINETEEN);
        map.put(DartboardNotifiedValue.TWENTY_INNER, Dart.TWENTY);

        map.put(DartboardNotifiedValue.ONE_OUTER, Dart.ONE);
        map.put(DartboardNotifiedValue.TWO_OUTER, Dart.TWO);
        map.put(DartboardNotifiedValue.THREE_OUTER, Dart.THREE);
        map.put(DartboardNotifiedValue.FOUR_OUTER, Dart.FOUR);
        map.put(DartboardNotifiedValue.FIVE_OUTER, Dart.FIVE);
        map.put(DartboardNotifiedValue.SIX_OUTER, Dart.SIX);
        map.put(DartboardNotifiedValue.SEVEN_OUTER, Dart.SEVEN);
        map.put(DartboardNotifiedValue.EIGHT_OUTER, Dart.EIGHT);
        map.put(DartboardNotifiedValue.NINE_OUTER, Dart.NINE);
        map.put(DartboardNotifiedValue.TEN_OUTER, Dart.TEN);
        map.put(DartboardNotifiedValue.ELEVEN_OUTER, Dart.ELEVEN);
        map.put(DartboardNotifiedValue.TWELVE_OUTER, Dart.TWELVE);
        map.put(DartboardNotifiedValue.THIRTEEN_OUTER, Dart.THIRTEEN);
        map.put(DartboardNotifiedValue.FOURTEEN_OUTER, Dart.FOURTEEN);
        map.put(DartboardNotifiedValue.FIFTEEN_OUTER, Dart.FIFTEEN);
        map.put(DartboardNotifiedValue.SIXTEEN_OUTER, Dart.SIXTEEN);
        map.put(DartboardNotifiedValue.SEVENTEEN_OUTER, Dart.SEVENTEEN);
        map.put(DartboardNotifiedValue.EIGHTEEN_OUTER, Dart.EIGHTEEN);
        map.put(DartboardNotifiedValue.NINETEEN_OUTER, Dart.NINETEEN);
        map.put(DartboardNotifiedValue.TWENTY_OUTER, Dart.TWENTY);

        map.put(DartboardNotifiedValue.BULLSEYE, Dart.BULLSEYE);

        map.put(DartboardNotifiedValue.DOUBLE_ONE, Dart.DOUBLE_ONE);
        map.put(DartboardNotifiedValue.DOUBLE_TWO, Dart.DOUBLE_TWO);
        map.put(DartboardNotifiedValue.DOUBLE_THREE, Dart.DOUBLE_THREE);
        map.put(DartboardNotifiedValue.DOUBLE_FOUR, Dart.DOUBLE_FOUR);
        map.put(DartboardNotifiedValue.DOUBLE_FIVE, Dart.DOUBLE_FIVE);
        map.put(DartboardNotifiedValue.DOUBLE_SIX, Dart.DOUBLE_SIX);
        map.put(DartboardNotifiedValue.DOUBLE_SEVEN, Dart.DOUBLE_SEVEN);
        map.put(DartboardNotifiedValue.DOUBLE_EIGHT, Dart.DOUBLE_EIGHT);
        map.put(DartboardNotifiedValue.DOUBLE_NINE, Dart.DOUBLE_NINE);
        map.put(DartboardNotifiedValue.DOUBLE_TEN, Dart.DOUBLE_TEN);
        map.put(DartboardNotifiedValue.DOUBLE_ELEVEN, Dart.DOUBLE_ELEVEN);
        map.put(DartboardNotifiedValue.DOUBLE_TWELVE, Dart.DOUBLE_TWELVE);
        map.put(DartboardNotifiedValue.DOUBLE_THIRTEEN, Dart.DOUBLE_THIRTEEN);
        map.put(DartboardNotifiedValue.DOUBLE_FOURTEEN, Dart.DOUBLE_FOURTEEN);
        map.put(DartboardNotifiedValue.DOUBLE_FIFTEEN, Dart.DOUBLE_FIFTEEN);
        map.put(DartboardNotifiedValue.DOUBLE_SIXTEEN, Dart.DOUBLE_SIXTEEN);
        map.put(DartboardNotifiedValue.DOUBLE_SEVENTEEN, Dart.DOUBLE_SEVENTEEN);
        map.put(DartboardNotifiedValue.DOUBLE_EIGHTEEN, Dart.DOUBLE_EIGHTEEN);
        map.put(DartboardNotifiedValue.DOUBLE_NINETEEN, Dart.DOUBLE_NINETEEN);
        map.put(DartboardNotifiedValue.DOUBLE_TWENTY, Dart.DOUBLE_TWENTY);

        map.put(DartboardNotifiedValue.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE);

        map.put(DartboardNotifiedValue.TRIPLE_ONE, Dart.TRIPLE_ONE);
        map.put(DartboardNotifiedValue.TRIPLE_TWO, Dart.TRIPLE_TWO);
        map.put(DartboardNotifiedValue.TRIPLE_THREE, Dart.TRIPLE_THREE);
        map.put(DartboardNotifiedValue.TRIPLE_FOUR, Dart.TRIPLE_FOUR);
        map.put(DartboardNotifiedValue.TRIPLE_FIVE, Dart.TRIPLE_FIVE);
        map.put(DartboardNotifiedValue.TRIPLE_SIX, Dart.TRIPLE_SIX);
        map.put(DartboardNotifiedValue.TRIPLE_SEVEN, Dart.TRIPLE_SEVEN);
        map.put(DartboardNotifiedValue.TRIPLE_EIGHT, Dart.TRIPLE_EIGHT);
        map.put(DartboardNotifiedValue.TRIPLE_NINE, Dart.TRIPLE_NINE);
        map.put(DartboardNotifiedValue.TRIPLE_TEN, Dart.TRIPLE_TEN);
        map.put(DartboardNotifiedValue.TRIPLE_ELEVEN, Dart.TRIPLE_ELEVEN);
        map.put(DartboardNotifiedValue.TRIPLE_TWELVE, Dart.TRIPLE_TWELVE);
        map.put(DartboardNotifiedValue.TRIPLE_THIRTEEN, Dart.TRIPLE_THIRTEEN);
        map.put(DartboardNotifiedValue.TRIPLE_FOURTEEN, Dart.TRIPLE_FOURTEEN);
        map.put(DartboardNotifiedValue.TRIPLE_FIFTEEN, Dart.TRIPLE_FIFTEEN);
        map.put(DartboardNotifiedValue.TRIPLE_SIXTEEN, Dart.TRIPLE_SIXTEEN);
        map.put(DartboardNotifiedValue.TRIPLE_SEVENTEEN, Dart.TRIPLE_SEVENTEEN);
        map.put(DartboardNotifiedValue.TRIPLE_EIGHTEEN, Dart.TRIPLE_EIGHTEEN);
        map.put(DartboardNotifiedValue.TRIPLE_NINETEEN, Dart.TRIPLE_NINETEEN);
        map.put(DartboardNotifiedValue.TRIPLE_TWENTY, Dart.TRIPLE_TWENTY);

        return Collections.unmodifiableMap(map);
    }
}
