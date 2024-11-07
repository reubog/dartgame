package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartgame.DartBoard;

import java.util.function.Consumer;

public interface DartboardFactory {
    void createDartboard(Consumer<DartBoard> dartBoardConsumer);
    void  destroyDartboard(DartBoard dartBoard);
}
