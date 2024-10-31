package com.bognandi.dartgame.domain.game;

import java.util.function.Consumer;

public class DefaultDartBoardAction implements DartBoardAction {
    private Consumer<Dart> dartConsumer;
    private Consumer<Void> buttonConsumer;

    @Override
    public void onDartThrown(Consumer<Dart> consumer) {
        dartConsumer = consumer;
    }

    @Override
    public void onButton(Consumer<Void> consumer) {
        buttonConsumer = consumer;
    }

    public void doThrowDart(Dart dart) {
        dartConsumer.accept(dart);
    }

    public void doPressButton() {
        buttonConsumer.accept(null);
    }
}
