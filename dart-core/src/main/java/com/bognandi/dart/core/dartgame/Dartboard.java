package com.bognandi.dart.core.dartgame;

import com.bognandi.dart.core.dartboard.DartboardStatus;

public interface Dartboard {
    DartboardStatus getStatus();
    void addEventListener(DartboardListener listener);
    void removeEventListener(DartboardListener listener);
}
