package com.bognandi.dartgame.domain.dartboard;

import com.bognandi.dartgame.domain.dartgame.DartBoard;
import com.bognandi.dartgame.domain.dartgame.DartBoardEventListener;

import java.util.ArrayList;
import java.util.List;

public class BluetoothDartboard implements DartBoard {

    private final List<DartBoardEventListener> listeners = new ArrayList<>();


    @Override
    public void addEventListener(DartBoardEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(DartBoardEventListener listener) {
        listeners.remove(listener);
    }
}
