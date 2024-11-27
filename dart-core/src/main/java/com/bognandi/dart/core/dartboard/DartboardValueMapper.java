package com.bognandi.dart.core.dartboard;

import com.bognandi.dart.core.dartgame.Dart;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DartboardValueMapper {

    public static Map<DartboardValue,Dart> DART_MAP = createDartMapping();
    public static Map<String, DartboardValue> BOARD_VALUE_MAP = createDartboardValueMapping();

    public static String bytesToHex(byte[] value) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<value.length; i++) {
            if (i>0) sb.append(" ");
            sb.append(String.format("%02X", value[i]));
        }
        return sb.toString();
    }

    public static DartboardValue bytesToDartboardNotifiedValue(byte[] value) {
        String hexString = bytesToHex(value);
        return BOARD_VALUE_MAP.get(hexString);
    }

    private static Map<String, DartboardValue> createDartboardValueMapping() {
        Map<String, DartboardValue> map = new LinkedHashMap<>();
        map.put("47 42 35 3B 31 30 31", DartboardValue.INITIAL_CODE);

        map.put("42 54 4E 40", DartboardValue.RED_BUTTON);
        map.put("32 2E 33 40", DartboardValue.ONE_INNER);
        map.put("39 2E 31 40", DartboardValue.TWO_INNER);
        map.put("37 2E 31 40", DartboardValue.THREE_INNER);
        map.put("30 2E 31 40", DartboardValue.FOUR_INNER);
        map.put("35 2E 31 40", DartboardValue.FIVE_INNER);
        map.put("31 2E 30 40", DartboardValue.SIX_INNER);
        map.put("31 31 2E 31 40", DartboardValue.SEVEN_INNER);
        map.put("36 2E 32 40", DartboardValue.EIGHT_INNER);
        map.put("39 2E 33 40", DartboardValue.NINE_INNER);
        map.put("32 2E 30 40", DartboardValue.TEN_INNER);
        map.put("37 2E 33 40", DartboardValue.ELEVEN_INNER);
        map.put("35 2E 30 40", DartboardValue.TWELVE_INNER);
        map.put("30 2E 30 40", DartboardValue.THIRTEEN_INNER);
        map.put("31 30 2E 33 40", DartboardValue.FOURTEEN_INNER);
        map.put("33 2E 30 40", DartboardValue.FIFTEEN_INNER);
        map.put("31 31 2E 30 40", DartboardValue.SIXTEEN_INNER);
        map.put("31 30 2E 31 40", DartboardValue.SEVENTEEN_INNER);
        map.put("31 2E 32 40", DartboardValue.EIGHTEEN_INNER);
        map.put("36 2E 31 40", DartboardValue.NINETEEN_INNER);
        map.put("33 2E 33 40", DartboardValue.TWENTY_INNER);

        map.put("32 2E 35 40", DartboardValue.ONE_OUTER);
        map.put("39 2E 32 40", DartboardValue.TWO_OUTER);
        map.put("37 2E 32 40", DartboardValue.THREE_OUTER);
        map.put("30 2E 35 40", DartboardValue.FOUR_OUTER);
        map.put("35 2E 34 40", DartboardValue.FIVE_OUTER);
        map.put("31 2E 33 40", DartboardValue.SIX_OUTER);
        map.put("31 31 2E 34 40", DartboardValue.SEVEN_OUTER);
        map.put("36 2E 35 40", DartboardValue.EIGHT_OUTER);
        map.put("39 2E 35 40", DartboardValue.NINE_OUTER);
        map.put("32 2E 32 40", DartboardValue.TEN_OUTER);
        map.put("37 2E 35 40", DartboardValue.ELEVEN_OUTER);
        map.put("35 2E 35 40", DartboardValue.TWELVE_OUTER);
        map.put("30 2E 34 40", DartboardValue.THIRTEEN_OUTER);
        map.put("31 30 2E 35 40", DartboardValue.FOURTEEN_OUTER);
        map.put("33 2E 32 40", DartboardValue.FIFTEEN_OUTER);
        map.put("31 31 2E 35 40", DartboardValue.SIXTEEN_OUTER);
        map.put("31 30 2E 32 40", DartboardValue.SEVENTEEN_OUTER);
        map.put("31 2E 35 40", DartboardValue.EIGHTEEN_OUTER);
        map.put("36 2E 33 40", DartboardValue.NINETEEN_OUTER);
        map.put("33 2E 35 40", DartboardValue.TWENTY_OUTER);

        map.put("38 2E 30 40", DartboardValue.BULLSEYE);

        map.put("32 2E 36 40", DartboardValue.DOUBLE_ONE);
        map.put("38 2E 32 40", DartboardValue.DOUBLE_TWO);
        map.put("38 2E 34 40", DartboardValue.DOUBLE_THREE);
        map.put("30 2E 36 40", DartboardValue.DOUBLE_FOUR);
        map.put("34 2E 36 40", DartboardValue.DOUBLE_FIVE);
        map.put("34 2E 34 40", DartboardValue.DOUBLE_SIX);
        map.put("38 2E 36 40", DartboardValue.DOUBLE_SEVEN);
        map.put("36 2E 36 40", DartboardValue.DOUBLE_EIGHT);
        map.put("39 2E 36 40", DartboardValue.DOUBLE_NINE);
        map.put("34 2E 33 40", DartboardValue.DOUBLE_TEN);
        map.put("37 2E 36 40", DartboardValue.DOUBLE_ELEVEN);
        map.put("35 2E 36 40", DartboardValue.DOUBLE_TWELVE);
        map.put("34 2E 35 40", DartboardValue.DOUBLE_THIRTEEN);
        map.put("31 30 2E 36 40", DartboardValue.DOUBLE_FOURTEEN);
        map.put("34 2E 32 40", DartboardValue.DOUBLE_FIFTEEN);
        map.put("31 31 2E 36 40", DartboardValue.DOUBLE_SIXTEEN);
        map.put("38 2E 33 40", DartboardValue.DOUBLE_SEVENTEEN);
        map.put("31 2E 36 40", DartboardValue.DOUBLE_EIGHTEEN);
        map.put("38 2E 35 40", DartboardValue.DOUBLE_NINETEEN);
        map.put("33 2E 36 40", DartboardValue.DOUBLE_TWENTY);

        map.put("34 2E 30 40", DartboardValue.DOUBLE_BULLSEYE);

        map.put("32 2E 34 40", DartboardValue.TRIPLE_ONE);
        map.put("39 2E 30 40", DartboardValue.TRIPLE_TWO);
        map.put("37 2E 30 40", DartboardValue.TRIPLE_THREE);
        map.put("30 2E 33 40", DartboardValue.TRIPLE_FOUR);
        map.put("35 2E 32 40", DartboardValue.TRIPLE_FIVE);
        map.put("31 2E 31 40", DartboardValue.TRIPLE_SIX);
        map.put("31 31 2E 32 40", DartboardValue.TRIPLE_SEVEN);
        map.put("36 2E 34 40", DartboardValue.TRIPLE_EIGHT);
        map.put("39 2E 34 40", DartboardValue.TRIPLE_NINE);
        map.put("32 2E 31 40", DartboardValue.TRIPLE_TEN);
        map.put("37 2E 34 40", DartboardValue.TRIPLE_ELEVEN);
        map.put("35 2E 33 40", DartboardValue.TRIPLE_TWELVE);
        map.put("30 2E 32 40", DartboardValue.TRIPLE_THIRTEEN);
        map.put("31 30 2E 34 40", DartboardValue.TRIPLE_FOURTEEN);
        map.put("33 2E 31 40", DartboardValue.TRIPLE_FIFTEEN);
        map.put("31 31 2E 33 40", DartboardValue.TRIPLE_SIXTEEN);
        map.put("31 30 2E 30 40", DartboardValue.TRIPLE_SEVENTEEN);
        map.put("31 2E 34 40", DartboardValue.TRIPLE_EIGHTEEN);
        map.put("36 2E 30 40", DartboardValue.TRIPLE_NINETEEN);
        map.put("33 2E 34 40", DartboardValue.TRIPLE_TWENTY);

        return Collections.unmodifiableMap(map);
    }

    private static Map<DartboardValue, Dart> createDartMapping() {
        Map<DartboardValue,Dart> map = new LinkedHashMap<>();

        map.put(DartboardValue.ONE_INNER, Dart.ONE);
        map.put(DartboardValue.TWO_INNER, Dart.TWO);
        map.put(DartboardValue.THREE_INNER, Dart.THREE);
        map.put(DartboardValue.FOUR_INNER, Dart.FOUR);
        map.put(DartboardValue.FIVE_INNER, Dart.FIVE);
        map.put(DartboardValue.SIX_INNER, Dart.SIX);
        map.put(DartboardValue.SEVEN_INNER, Dart.SEVEN);
        map.put(DartboardValue.EIGHT_INNER, Dart.EIGHT);
        map.put(DartboardValue.NINE_INNER, Dart.NINE);
        map.put(DartboardValue.TEN_INNER, Dart.TEN);
        map.put(DartboardValue.ELEVEN_INNER, Dart.ELEVEN);
        map.put(DartboardValue.TWELVE_INNER, Dart.TWELVE);
        map.put(DartboardValue.THIRTEEN_INNER, Dart.THIRTEEN);
        map.put(DartboardValue.FOURTEEN_INNER, Dart.FOURTEEN);
        map.put(DartboardValue.FIFTEEN_INNER, Dart.FIFTEEN);
        map.put(DartboardValue.SIXTEEN_INNER, Dart.SIXTEEN);
        map.put(DartboardValue.SEVENTEEN_INNER, Dart.SEVENTEEN);
        map.put(DartboardValue.EIGHTEEN_INNER, Dart.EIGHTEEN);
        map.put(DartboardValue.NINETEEN_INNER, Dart.NINETEEN);
        map.put(DartboardValue.TWENTY_INNER, Dart.TWENTY);

        map.put(DartboardValue.ONE_OUTER, Dart.ONE);
        map.put(DartboardValue.TWO_OUTER, Dart.TWO);
        map.put(DartboardValue.THREE_OUTER, Dart.THREE);
        map.put(DartboardValue.FOUR_OUTER, Dart.FOUR);
        map.put(DartboardValue.FIVE_OUTER, Dart.FIVE);
        map.put(DartboardValue.SIX_OUTER, Dart.SIX);
        map.put(DartboardValue.SEVEN_OUTER, Dart.SEVEN);
        map.put(DartboardValue.EIGHT_OUTER, Dart.EIGHT);
        map.put(DartboardValue.NINE_OUTER, Dart.NINE);
        map.put(DartboardValue.TEN_OUTER, Dart.TEN);
        map.put(DartboardValue.ELEVEN_OUTER, Dart.ELEVEN);
        map.put(DartboardValue.TWELVE_OUTER, Dart.TWELVE);
        map.put(DartboardValue.THIRTEEN_OUTER, Dart.THIRTEEN);
        map.put(DartboardValue.FOURTEEN_OUTER, Dart.FOURTEEN);
        map.put(DartboardValue.FIFTEEN_OUTER, Dart.FIFTEEN);
        map.put(DartboardValue.SIXTEEN_OUTER, Dart.SIXTEEN);
        map.put(DartboardValue.SEVENTEEN_OUTER, Dart.SEVENTEEN);
        map.put(DartboardValue.EIGHTEEN_OUTER, Dart.EIGHTEEN);
        map.put(DartboardValue.NINETEEN_OUTER, Dart.NINETEEN);
        map.put(DartboardValue.TWENTY_OUTER, Dart.TWENTY);

        map.put(DartboardValue.BULLSEYE, Dart.BULLSEYE);

        map.put(DartboardValue.DOUBLE_ONE, Dart.DOUBLE_ONE);
        map.put(DartboardValue.DOUBLE_TWO, Dart.DOUBLE_TWO);
        map.put(DartboardValue.DOUBLE_THREE, Dart.DOUBLE_THREE);
        map.put(DartboardValue.DOUBLE_FOUR, Dart.DOUBLE_FOUR);
        map.put(DartboardValue.DOUBLE_FIVE, Dart.DOUBLE_FIVE);
        map.put(DartboardValue.DOUBLE_SIX, Dart.DOUBLE_SIX);
        map.put(DartboardValue.DOUBLE_SEVEN, Dart.DOUBLE_SEVEN);
        map.put(DartboardValue.DOUBLE_EIGHT, Dart.DOUBLE_EIGHT);
        map.put(DartboardValue.DOUBLE_NINE, Dart.DOUBLE_NINE);
        map.put(DartboardValue.DOUBLE_TEN, Dart.DOUBLE_TEN);
        map.put(DartboardValue.DOUBLE_ELEVEN, Dart.DOUBLE_ELEVEN);
        map.put(DartboardValue.DOUBLE_TWELVE, Dart.DOUBLE_TWELVE);
        map.put(DartboardValue.DOUBLE_THIRTEEN, Dart.DOUBLE_THIRTEEN);
        map.put(DartboardValue.DOUBLE_FOURTEEN, Dart.DOUBLE_FOURTEEN);
        map.put(DartboardValue.DOUBLE_FIFTEEN, Dart.DOUBLE_FIFTEEN);
        map.put(DartboardValue.DOUBLE_SIXTEEN, Dart.DOUBLE_SIXTEEN);
        map.put(DartboardValue.DOUBLE_SEVENTEEN, Dart.DOUBLE_SEVENTEEN);
        map.put(DartboardValue.DOUBLE_EIGHTEEN, Dart.DOUBLE_EIGHTEEN);
        map.put(DartboardValue.DOUBLE_NINETEEN, Dart.DOUBLE_NINETEEN);
        map.put(DartboardValue.DOUBLE_TWENTY, Dart.DOUBLE_TWENTY);

        map.put(DartboardValue.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE);

        map.put(DartboardValue.TRIPLE_ONE, Dart.TRIPLE_ONE);
        map.put(DartboardValue.TRIPLE_TWO, Dart.TRIPLE_TWO);
        map.put(DartboardValue.TRIPLE_THREE, Dart.TRIPLE_THREE);
        map.put(DartboardValue.TRIPLE_FOUR, Dart.TRIPLE_FOUR);
        map.put(DartboardValue.TRIPLE_FIVE, Dart.TRIPLE_FIVE);
        map.put(DartboardValue.TRIPLE_SIX, Dart.TRIPLE_SIX);
        map.put(DartboardValue.TRIPLE_SEVEN, Dart.TRIPLE_SEVEN);
        map.put(DartboardValue.TRIPLE_EIGHT, Dart.TRIPLE_EIGHT);
        map.put(DartboardValue.TRIPLE_NINE, Dart.TRIPLE_NINE);
        map.put(DartboardValue.TRIPLE_TEN, Dart.TRIPLE_TEN);
        map.put(DartboardValue.TRIPLE_ELEVEN, Dart.TRIPLE_ELEVEN);
        map.put(DartboardValue.TRIPLE_TWELVE, Dart.TRIPLE_TWELVE);
        map.put(DartboardValue.TRIPLE_THIRTEEN, Dart.TRIPLE_THIRTEEN);
        map.put(DartboardValue.TRIPLE_FOURTEEN, Dart.TRIPLE_FOURTEEN);
        map.put(DartboardValue.TRIPLE_FIFTEEN, Dart.TRIPLE_FIFTEEN);
        map.put(DartboardValue.TRIPLE_SIXTEEN, Dart.TRIPLE_SIXTEEN);
        map.put(DartboardValue.TRIPLE_SEVENTEEN, Dart.TRIPLE_SEVENTEEN);
        map.put(DartboardValue.TRIPLE_EIGHTEEN, Dart.TRIPLE_EIGHTEEN);
        map.put(DartboardValue.TRIPLE_NINETEEN, Dart.TRIPLE_NINETEEN);
        map.put(DartboardValue.TRIPLE_TWENTY, Dart.TRIPLE_TWENTY);

        return Collections.unmodifiableMap(map);
    }
}
