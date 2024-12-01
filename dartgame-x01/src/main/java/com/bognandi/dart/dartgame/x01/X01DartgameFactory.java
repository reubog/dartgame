package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.*;

public class X01DartgameFactory {

    private static final DartValueMapper DART_VALUE_MAPPER = new DefaultDartValueMapper();

    public static class Dartgame301 implements DartgameFactory {

        private static final DartgameDescriptor DESCRIPTOR = new X01DartgameDescriptor("301");

        public Dartgame301() {
        }

        @Override
        public DartgameDescriptor getDartgameDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public Dartgame createDartgame() {
            return new X01Dartgame(new X01ScoreBoard(301, DART_VALUE_MAPPER));
        }
    }

    public static class Dartgame501 implements DartgameFactory {

        private static final DartgameDescriptor DESCRIPTOR = new X01DartgameDescriptor("501");

        public Dartgame501() {
        }

        @Override
        public DartgameDescriptor getDartgameDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public Dartgame createDartgame() {
            return new X01Dartgame(new X01ScoreBoard(501, DART_VALUE_MAPPER));
        }
    }

    public static class Dartgame701 implements DartgameFactory {

        private static final DartgameDescriptor DESCRIPTOR = new X01DartgameDescriptor("701");

        public Dartgame701() {
        }

        @Override
        public DartgameDescriptor getDartgameDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public Dartgame createDartgame() {
            return new X01Dartgame(new X01ScoreBoard(701, DART_VALUE_MAPPER));
        }
    }

    public static class Dartgame901 implements DartgameFactory {

        private static final DartgameDescriptor DESCRIPTOR = new X01DartgameDescriptor("901");

        public Dartgame901() {
        }

        @Override
        public DartgameDescriptor getDartgameDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public Dartgame createDartgame() {
            return new X01Dartgame(new X01ScoreBoard(901, DART_VALUE_MAPPER));
        }
    }


}
