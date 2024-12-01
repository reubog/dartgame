package com.bognandi.dart.core.dartgame;

public interface DartgameFactory {
    DartgameDescriptor getDartgameDescriptor();
    Dartgame createDartgame();
}
