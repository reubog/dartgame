package com.bognandi.dart.core.dartgame;

public abstract class DartGameException extends RuntimeException {
    public DartGameException(String message) {
        super(message);
    }
    public DartGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
