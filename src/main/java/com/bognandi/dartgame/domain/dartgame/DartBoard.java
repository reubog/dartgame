package com.bognandi.dartgame.domain.dartgame;

public interface DartBoard {
    void addEventListener(DartBoardEventListener listener);
    void removeEventListener(DartBoardEventListener listener);
}
