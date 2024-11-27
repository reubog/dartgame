package com.bognandi.dart.domain.x01game;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardValueMapper;
import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.Dartboard;
import com.bognandi.dart.core.dartgame.DartboardListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImplDartboard implements Dartboard {

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

    public void doThrowDart(Dart dart) {
        eventListeners.forEach(listener -> listener.onDartboardValue(DartboardValueMapper.DART_MAP.entrySet().stream()
                .filter(entry -> entry.getValue() == dart)
                .map(Map.Entry::getKey)
                .findFirst()
                .get()));
    }

    public void doPressButton() {
        eventListeners.forEach(listener -> listener.onDartboardValue(DartboardValue.RED_BUTTON));
    }
}
