package com.bognandi.dart.core.dartgame;

import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardStatus;

public interface DartboardListener {
    void onStatusChange(DartboardStatus status);
    void onDartboardValue(DartboardValue value);
}
