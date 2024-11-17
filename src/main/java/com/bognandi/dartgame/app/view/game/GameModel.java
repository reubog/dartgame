package com.bognandi.dartgame.app.view.game;

import com.bognandi.dartgame.app.service.GameAppService;
import com.bognandi.dartgame.domain.dartgame.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class GameModel {

    public static final UUID GAME_STARTING = UUID.randomUUID();
    public static final UUID GAME_STARTED = UUID.randomUUID();

    private final Notifications notifications = Notifications.createInstance();
    private final DartgameListener dartgameListener = new DartgameListener();
    private Dartgame dartgame;

    @Autowired
    private GameAppService gameAppService;

    public void initGame(String id) {
//        this.dartgame = dartgameFactory.createDartgame(id);
        this.dartgame.addEventListener(dartgameListener);
    }

    public void startGame() {
        dartgame.startGame();
    }

    public void addPlayer(String name) {
        dartgame.addPlayer(new DefaultPlayer(name));
    }

    public void exitGame() {
        dartgame.removeEventListener(dartgameListener);
    }

    private class DartgameListener extends DefaultDartgameEventListener {
        @Override
        public void onGameStarting(Dartgame dartGame) {
            super.onGameStarting(dartGame);
            notifications.publish(GAME_STARTING);
        }

        @Override
        public void onGameStarted(Dartgame dartGame) {
            super.onGameStarted(dartGame);
            notifications.publish(GAME_STARTED);
        }
    }
}
