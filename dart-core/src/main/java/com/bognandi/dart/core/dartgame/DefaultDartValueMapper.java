package com.bognandi.dart.core.dartgame;

public class DefaultDartValueMapper implements DartValueMapper {

    @Override
    public int getDartScore(Dart dart) {
        return mapDartToScore(dart);
    }

    @Override
    public int getDartValue(Dart dart) {
        return mapDartToValue(dart);
    }

    @Override
    public int multiplier(Dart dart) {
        return mapDartToMultiplier(dart);
    }

    private int mapDartToMultiplier(Dart dart) {
        switch (dart) {
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
            case TEN:
            case ELEVEN:
            case TWELVE:
            case THIRTEEN:
            case FOURTEEN:
            case FIFTEEN:
            case SIXTEEN:
            case SEVENTEEN:
            case EIGHTEEN:
            case NINETEEN:
            case TWENTY:
            case BULLSEYE:
                return 1;

            case DOUBLE_ONE:
            case DOUBLE_TWO:
            case DOUBLE_THREE:
            case DOUBLE_FOUR:
            case DOUBLE_FIVE:
            case DOUBLE_SIX:
            case DOUBLE_SEVEN:
            case DOUBLE_EIGHT:
            case DOUBLE_NINE:
            case DOUBLE_TEN:
            case DOUBLE_ELEVEN:
            case DOUBLE_TWELVE:
            case DOUBLE_THIRTEEN:
            case DOUBLE_FOURTEEN:
            case DOUBLE_FIFTEEN:
            case DOUBLE_SIXTEEN:
            case DOUBLE_SEVENTEEN:
            case DOUBLE_EIGHTEEN:
            case DOUBLE_NINETEEN:
            case DOUBLE_TWENTY:
            case DOUBLE_BULLSEYE:
                return 2;

            case TRIPLE_ONE:
            case TRIPLE_TWO:
            case TRIPLE_THREE:
            case TRIPLE_FOUR:
            case TRIPLE_FIVE:
            case TRIPLE_SIX:
            case TRIPLE_SEVEN:
            case TRIPLE_EIGHT:
            case TRIPLE_NINE:
            case TRIPLE_TEN:
            case TRIPLE_ELEVEN:
            case TRIPLE_TWELVE:
            case TRIPLE_THIRTEEN:
            case TRIPLE_FOURTEEN:
            case TRIPLE_FIFTEEN:
            case TRIPLE_SIXTEEN:
            case TRIPLE_SEVENTEEN:
            case TRIPLE_EIGHTEEN:
            case TRIPLE_NINETEEN:
            case TRIPLE_TWENTY:
                return 3;

            default:
                return 0;

        }
    }

    private int mapDartToValue(Dart dart) {
        switch (dart) {
            case ONE:
            case DOUBLE_ONE:
            case TRIPLE_ONE:
                return 1;

            case TWO:
            case DOUBLE_TWO:
            case TRIPLE_TWO:
                return 2;

            case THREE:
            case DOUBLE_THREE:
            case TRIPLE_THREE:
                return 3;

            case FOUR:
            case DOUBLE_FOUR:
            case TRIPLE_FOUR:
                return 4;

            case FIVE:
            case DOUBLE_FIVE:
            case TRIPLE_FIVE:
                return 5;


            case SIX:
            case DOUBLE_SIX:
            case TRIPLE_SIX:
                return 6;

            case SEVEN:
            case DOUBLE_SEVEN:
            case TRIPLE_SEVEN:
                return 7;


            case EIGHT:
            case DOUBLE_EIGHT:
            case TRIPLE_EIGHT:
                return 8;

            case NINE:
            case DOUBLE_NINE:
            case TRIPLE_NINE:
                return 9;

            case TEN:
            case DOUBLE_TEN:
            case TRIPLE_TEN:
                return 10;

            case ELEVEN:
            case DOUBLE_ELEVEN:
            case TRIPLE_ELEVEN:
                return 11;

            case TWELVE:
            case DOUBLE_TWELVE:
            case TRIPLE_TWELVE:
                return 12;

            case THIRTEEN:
            case DOUBLE_THIRTEEN:
            case TRIPLE_THIRTEEN:
                return 13;

            case FOURTEEN:
            case DOUBLE_FOURTEEN:
            case TRIPLE_FOURTEEN:
                return 14;

            case FIFTEEN:
            case DOUBLE_FIFTEEN:
            case TRIPLE_FIFTEEN:
                return 15;

            case SIXTEEN:
            case DOUBLE_SIXTEEN:
            case TRIPLE_SIXTEEN:
                return 16;

            case SEVENTEEN:
            case DOUBLE_SEVENTEEN:
            case TRIPLE_SEVENTEEN:
                return 17;

            case EIGHTEEN:
            case DOUBLE_EIGHTEEN:
            case TRIPLE_EIGHTEEN:
                return 18;

            case NINETEEN:
            case DOUBLE_NINETEEN:
            case TRIPLE_NINETEEN:
                return 19;

            case TWENTY:
            case DOUBLE_TWENTY:
            case TRIPLE_TWENTY:
                return 20;

            case BULLSEYE:
            case DOUBLE_BULLSEYE:
                return 25;

            default:
                return 0;
        }
    }

    private int mapDartToScore(Dart dart) {
        switch (dart) {
            case DOUBLE_BULLSEYE:
                return 50;
            case BULLSEYE:
                return 25;
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case ELEVEN:
                return 11;
            case TWELVE:
                return 12;
            case THIRTEEN:
                return 13;
            case FOURTEEN:
                return 14;
            case FIFTEEN:
                return 15;
            case SIXTEEN:
                return 16;
            case SEVENTEEN:
                return 17;
            case EIGHTEEN:
                return 18;
            case NINETEEN:
                return 19;
            case TWENTY:
                return 20;
            case DOUBLE_ONE:
                return 2;
            case DOUBLE_TWO:
                return 4;
            case DOUBLE_THREE:
                return 6;
            case DOUBLE_FOUR:
                return 8;
            case DOUBLE_FIVE:
                return 10;
            case DOUBLE_SIX:
                return 12;
            case DOUBLE_SEVEN:
                return 14;
            case DOUBLE_EIGHT:
                return 16;
            case DOUBLE_NINE:
                return 18;
            case DOUBLE_TEN:
                return 20;
            case DOUBLE_ELEVEN:
                return 22;
            case DOUBLE_TWELVE:
                return 24;
            case DOUBLE_THIRTEEN:
                return 26;
            case DOUBLE_FOURTEEN:
                return 28;
            case DOUBLE_FIFTEEN:
                return 30;
            case DOUBLE_SIXTEEN:
                return 32;
            case DOUBLE_SEVENTEEN:
                return 34;
            case DOUBLE_EIGHTEEN:
                return 36;
            case DOUBLE_NINETEEN:
                return 38;
            case DOUBLE_TWENTY:
                return 40;
            case TRIPLE_ONE:
                return 3;
            case TRIPLE_TWO:
                return 6;
            case TRIPLE_THREE:
                return 9;
            case TRIPLE_FOUR:
                return 12;
            case TRIPLE_FIVE:
                return 15;
            case TRIPLE_SIX:
                return 18;
            case TRIPLE_SEVEN:
                return 21;
            case TRIPLE_EIGHT:
                return 24;
            case TRIPLE_NINE:
                return 27;
            case TRIPLE_TEN:
                return 30;
            case TRIPLE_ELEVEN:
                return 33;
            case TRIPLE_TWELVE:
                return 36;
            case TRIPLE_THIRTEEN:
                return 39;
            case TRIPLE_FOURTEEN:
                return 42;
            case TRIPLE_FIFTEEN:
                return 45;
            case TRIPLE_SIXTEEN:
                return 48;
            case TRIPLE_SEVENTEEN:
                return 51;
            case TRIPLE_EIGHTEEN:
                return 54;
            case TRIPLE_NINETEEN:
                return 57;
            case TRIPLE_TWENTY:
                return 60;
            default:
                return 0;
        }
    }
}
