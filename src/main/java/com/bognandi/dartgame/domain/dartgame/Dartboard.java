package com.bognandi.dartgame.domain.dartgame;

public interface Dartboard {
    void addEventListener(DartBoardEventListener listener);
    void removeEventListener(DartBoardEventListener listener);
}
