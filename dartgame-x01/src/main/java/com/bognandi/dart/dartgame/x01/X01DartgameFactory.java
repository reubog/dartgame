package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.*;

public class X01DartgameFactory implements DartgameFactory {

    private static final DartValueMapper DART_VALUE_MAPPER = new DefaultDartValueMapper();
    private static final DartgameDescriptor DESCRIPTOR = new DartgameDescriptor() {
        @Override
        public String getTitle() {
            return "301";
        }

        @Override
        public String getDescrption() {
            return """
                    Play 301 with at least 2 players. All players start with score 301. 
                    Each throw reduces the score until zero is reached and the player wins.
                    Throwing darts that goes beyond zero into the negative score will disregard
                    all darts in the whole round.
                    """;
        }
    };

    @Override
    public DartgameDescriptor getDartgameDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public Dartgame createDartgame() {
        return new X01Dartgame(new X01ScoreBoard(301, DART_VALUE_MAPPER));
    }
}
