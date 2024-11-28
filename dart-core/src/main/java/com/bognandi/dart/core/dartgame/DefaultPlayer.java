package com.bognandi.dart.core.dartgame;

public class DefaultPlayer implements Player {
    private final String name;

    public DefaultPlayer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}