package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.DartgameDescriptor;

public class X01DartgameDescriptor implements DartgameDescriptor {

    private static final String DESCRIPTION = """
                    Play <TITLE> with at least 2 players. All players start with score <TITLE>. 
                    Each throw reduces the score until zero is reached and the player wins.
                    Throwing darts that goes beyond zero into the negative score will disregard
                    all darts in the whole round.
                    """;

    private String title;

    public X01DartgameDescriptor(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescrption() {
        return DESCRIPTION.replace("<TITLE>", title);
    }
}
