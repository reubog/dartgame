package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardValueMapper;
import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.Dartboard;
import com.bognandi.dart.core.dartgame.DartboardListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ImplDartboard implements Dartboard {

    private List<DartboardListener> eventListeners = new ArrayList<>();
    private DartboardStatus status;
    private DartboardValueMapper mapper = new DartboardValueMapper();
    private Consumer<DartboardStatus> statusConsumer;
    private Consumer<DartboardValue> valueConsumer;

    @Override
    public DartboardStatus getStatus() {
        return status;
    }

    public void doThrowDart(Dart dart) {
        valueConsumer.accept(DartboardValueMapper.DART_MAP.entrySet().stream()
                .filter(entry -> entry.getValue() == dart)
                .map(Map.Entry::getKey)
                .findFirst()
                .get());
    }

    public void doPressButton() {
        valueConsumer.accept(DartboardValue.RED_BUTTON);
    }

    @Override
    public void setOnStatusChange(Consumer<DartboardStatus> statusConsumer) {
        this.statusConsumer = statusConsumer;
    }

    @Override
    public void setOnDartboardValue(Consumer<DartboardValue> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }
}
