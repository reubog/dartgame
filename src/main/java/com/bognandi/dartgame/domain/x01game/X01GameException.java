package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.DartGameException;

public class X01GameException extends DartGameException {
    public X01GameException(String message) {
        super(message);
    }

    public X01GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
