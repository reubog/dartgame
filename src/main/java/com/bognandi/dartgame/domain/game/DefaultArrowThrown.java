package com.bognandi.dartgame.domain.game;

public class DefaultArrowThrown implements ArrowThrown {

    private ArrowValue arrowValue;
    private ArrowMultiplier arrowMultiplier;

    public DefaultArrowThrown(ArrowValue arrowValue, ArrowMultiplier arrowMultiplier) {
        this.arrowValue = arrowValue;
        this.arrowMultiplier = arrowMultiplier;
    }

    @Override
    public ArrowValue getArrowValue() {
        return null;
    }

    @Override
    public ArrowMultiplier getArrowMultiplier() {
        return null;
    }
}
