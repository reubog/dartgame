package com.bognandi.dartgame.app.view.gameselection;

import com.bognandi.dartgame.app.config.BeanConfig;
import com.bognandi.dartgame.domain.dartgame.DartgameDescriptor;
import com.bognandi.dartgame.domain.dartgame.DartgameFactory;

import java.util.List;

public class GameSelectionModel {

    private DartgameFactory dartgameFactory = BeanConfig.DARTGAME_FACTORY;

    public List<DartgameDescriptor> getGames() {
        return dartgameFactory.getDartgames();
    }
}
