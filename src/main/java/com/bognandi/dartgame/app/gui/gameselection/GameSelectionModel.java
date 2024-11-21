package com.bognandi.dartgame.app.gui.gameselection;

import com.bognandi.dartgame.app.service.GameAppService;
import com.bognandi.dartgame.domain.dartgame.DartgameDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class GameSelectionModel {

    @Autowired
    private GameAppService gameAppService;

    private Map<GameInfo, DartgameDescriptor> gameInfoMap;

    public List<GameInfo> getGames() {
        if (gameInfoMap == null) {
            gameInfoMap = new LinkedHashMap<>();
            gameAppService.getAvailableDartgames().stream()
                    .collect(Collectors.toMap(this::toGameInfo, descriptor -> descriptor));
        }
        return gameInfoMap.keySet().stream().toList();
    }

    private GameInfo toGameInfo(DartgameDescriptor descriptor) {
        return new GameInfo(descriptor.getId(), descriptor.getName());
    }

    public void selectGame(GameInfo gameInfo) {
        gameAppService.selectGame(gameInfoMap.get(gameInfo));
    }

}
