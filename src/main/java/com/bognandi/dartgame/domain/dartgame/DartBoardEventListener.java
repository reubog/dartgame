package com.bognandi.dartgame.domain.dartgame;

public interface DartBoardEventListener {
    void onDartThrown(Dart dart);
    void onButton();
}
