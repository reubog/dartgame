package com.bognandi.dartgame.domain.game;

import java.util.function.Consumer;

public interface DartBoardAction {
    void onDartThrown(Consumer<Dart> consumer);
    void onButton(Consumer<Void> consumer);
}
