package com.bognandi.dartgame.domain.game;

public interface DartBoardEventListener {
    void onDartThrown(Dart dart);
    void onButton();
}
