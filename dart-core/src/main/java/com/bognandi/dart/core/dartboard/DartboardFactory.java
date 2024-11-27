package com.bognandi.dart.core.dartboard;

import com.bognandi.dart.core.dartgame.Dartboard;

public interface DartboardFactory {
    Dartboard createDartboard();
    void destroyDartboard(Dartboard dartBoard);
    void shutdown();
}
