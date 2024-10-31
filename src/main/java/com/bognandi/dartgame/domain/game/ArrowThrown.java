package com.bognandi.dartgame.domain.game;

public interface ArrowThrown {
    ArrowValue getArrowValue();
    ArrowMultiplier getArrowMultiplier();

    static ArrowThrown of(ArrowValue arrowValue, ArrowMultiplier arrowMultiplier) {
        return new ArrowThrown() {
            @Override
            public ArrowValue getArrowValue() {
                return arrowValue;
            }

            @Override
            public ArrowMultiplier getArrowMultiplier() {
                return arrowMultiplier;
            }
        };
    }
}
