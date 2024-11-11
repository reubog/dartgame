package com.bognandi.dartgame.app.view.game;

import com.bognandi.dartgame.domain.dartgame.Dart;
import com.bognandi.dartgame.domain.dartgame.DartGame;
import com.bognandi.dartgame.domain.dartgame.DartGameEventListener;
import com.bognandi.dartgame.domain.dartgame.Player;

public class GameModel implements DartGameEventListener {

    @Override
    public void onGameStarting(DartGame dartGame) {

    }

    @Override
    public void onGameStarted(DartGame dartGame) {

    }

    @Override
    public void onGameFinished(DartGame dartGame) {

    }

    @Override
    public void onPlayerAdded(DartGame dartGame, Player player) {

    }

    @Override
    public void onRoundStarted(DartGame dartGame, int roundNumber) {

    }

    @Override
    public void onPlayerTurn(DartGame dartGame, int roundNumber, Player player) {

    }

    @Override
    public void onDartThrown(DartGame dartGame, Dart dart) {

    }

    @Override
    public void onRemoveDarts(DartGame dartGame, int round, Player player) {

    }

    @Override
    public void onPlayerWon(DartGame dartGame, Player player) {

    }

    @Override
    public void onPlayerBust(DartGame dartGame, Player player) {

    }

    @Override
    public void onPlayerLost(DartGame dartGame, Player player) {

    }
}
