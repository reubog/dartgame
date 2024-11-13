package com.bognandi.dartgame.domain.dartgame;

import java.util.List;

public interface DartgameFactory {
    List<DartgameDescriptor> getDartgames();
    Dartgame createDartgame(String id);
}
