package com.bognandi.dartgame.domain.game;

public abstract class DartGameException extends RuntimeException {
    public DartGameException(String message) {
        super(message);
    }
    public DartGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
