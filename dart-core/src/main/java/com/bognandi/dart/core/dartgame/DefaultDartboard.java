package com.bognandi.dart.core.dartgame;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValueMapper;

import java.util.ArrayList;
import java.util.List;

public class DefaultDartboard implements Dartboard {

    private List<DartboardListener> eventListeners = new ArrayList<>();
    private DartboardStatus status;
    private DartboardValueMapper mapper = new DartboardValueMapper();

    @Override
    public DartboardStatus getStatus() {
        return status;
    }

    @Override
    public void addEventListener(DartboardListener listener) {
        eventListeners.add(listener);
    }

    @Override
    public void removeEventListener(DartboardListener listener) {
        eventListeners.remove(listener);
    }
}
