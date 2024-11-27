package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface DartgameFactory {
    List<DartgameDescriptor> getDartgames();
    Dartgame createDartgame(String id);
}
