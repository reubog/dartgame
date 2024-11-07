package com.bognandi.dartgame.domain.dartgame;

import java.util.ArrayList;
import java.util.List;

public class DefaultDartboard implements Dartboard {

    private List<DartBoardEventListener> eventListeners = new ArrayList<>();

    @Override
    public void addEventListener(DartBoardEventListener listener) {
        eventListeners.add(listener);
    }

    @Override
    public void removeEventListener(DartBoardEventListener listener) {
        eventListeners.remove(listener);
    }

    public void doThrowDart(Dart dart) {
        eventListeners.forEach(listener -> listener.onDartThrown(dart));
    }

    public void doPressButton() {
        eventListeners.forEach(listener -> listener.onButton());
    }
}
