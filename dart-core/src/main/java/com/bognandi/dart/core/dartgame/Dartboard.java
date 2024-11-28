package com.bognandi.dart.core.dartgame;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;

import java.util.function.Consumer;

public interface Dartboard {
    DartboardStatus getStatus();
//    void addEventListener(DartboardListener listener);
//    void removeEventListener(DartboardListener listener);

    void setOnStatusChange(Consumer<DartboardStatus> statusConsumer);
    void setOnDartboardValue(Consumer<DartboardValue> valueConsumer);
}
