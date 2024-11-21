package com.bognandi.dartgame.app.gui.game;

import com.bognandi.dartgame.domain.dartgame.*;
import com.bognandi.dartgame.domain.dartgame.Player;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    @Autowired
    private Notifications notifications;

    @Autowired
    private DartValueMapper dartValueMapper;

    private final DartgameListener dartgameListener = new DartgameListener();
    private Dartgame dartgame;
    private int currentRound;
    private Player currentPlayer;
    private int currentPlayerRoundScore;
    private List<Dart> currentRoundDarts = new ArrayList<>();

    public void startGame(Dartgame dartgame) {
        this.dartgame = dartgame;
        this.dartgame.addEventListener(dartgameListener);
        this.dartgame.startGame();
    }

    public void finishGame() {
        dartgame.removeEventListener(dartgameListener);
    }

    int getCurrentRound() {
        return currentRound;
    }

    int getCurrentPlayerRoundScore() {
        return currentPlayerRoundScore;
    }

    List<Player> getPlayers() {
        return dartgame.getPlayers();
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    int getCurrentPlayerScore() {
        return dartgame.getPlayerScore(currentPlayer).getScore();
    }

    private class DartgameListener extends DefaultDartgameEventListener {
        @Override
        public void onGameStarting(Dartgame dartGame) {
            super.onGameStarting(dartGame);
            notifications.publish(Notifications.WAITING_FOR_PLAYERS);
        }

        @Override
        public void onGameStarted(Dartgame dartGame) {
            super.onGameStarted(dartGame);
            notifications.publish(Notifications.START_GAME);
        }

        @Override
        public void onPlayerAdded(Dartgame dartGame, Player player) {
            super.onPlayerAdded(dartGame,  currentPlayer = player);
            notifications.publish(Notifications.PLAYER_ADDED);
        }

        @Override
        public void onRoundStarted(Dartgame dartGame, int roundNumber) {
            super.onRoundStarted(dartGame, currentRound = roundNumber);
            currentPlayerRoundScore = 0;
            currentRoundDarts.clear();
            notifications.publish(Notifications.ROUND_STARTED);
        }

        @Override
        public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
            super.onPlayerTurn(dartGame, roundNumber, player);
            currentPlayer = player;
            currentPlayerRoundScore = 0;
            currentRoundDarts.clear();
            notifications.publish(Notifications.PLAYER_TURN);
        }

        @Override
        public void onDartThrown(Dartgame dartGame, Dart dart) {
            super.onDartThrown(dartGame, dart);
            currentPlayerRoundScore += dartValueMapper.getDartValue(dart);
            currentRoundDarts.add(dart);
            notifications.publish(Notifications.DART_THROWN);
        }
    }
}
