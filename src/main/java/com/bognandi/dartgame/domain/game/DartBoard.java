package com.bognandi.dartgame.domain.game;

public interface DartBoard {
    void addEventListener(DartBoardEventListener listener);
    void removeEventListener(DartBoardEventListener listener);
}
