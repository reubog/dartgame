package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartgame.Dartboard;

import java.util.function.Consumer;

public interface DartboardFactory {
    void createDartboard(String name, Consumer<Dartboard> dartBoardConsumer);
    void destroyDartboard(Dartboard dartBoard);
    void shutdown();
}
