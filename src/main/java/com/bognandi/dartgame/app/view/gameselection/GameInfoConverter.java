package com.bognandi.dartgame.app.view.gameselection;

import com.bognandi.dartgame.domain.dartgame.DartgameDescriptor;

public class GameInfoConverter {
    public GameInfo toGameInfo(DartgameDescriptor descriptor) {
        return new GameInfo(descriptor.getId(), descriptor.getName());
    }
}
