package com.bognandi.dartgame.app.service;

import com.bognandi.dartgame.app.gui.game.Notifications;
import com.bognandi.dartgame.domain.dartgame.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameService {

    @Autowired
    private Notifications notifications;

    @Autowired
    private DartValueMapper dartValueMapper;

    private final DefaultDartgameEventListener dartgameListener = new DartgameListener();
    private Dartgame dartgame;
    private int currentRound;
    private Player currentPlayer;
    private int currentPlayerRoundScore;
    private Dart lastDart;
    private List<Dart> currentRoundDarts = new ArrayList<>();

    public void startGame(Dartgame dartgame) {
        this.dartgame = dartgame;
        this.dartgame.addEventListener(dartgameListener);
        this.dartgame.startGame();
    }

    public void addPlayer(String name) {
        dartgame.addPlayer(new DefaultPlayer(name));
    }

    public void finishGame() {
        dartgame.removeEventListener(dartgameListener);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentPlayerRoundScore() {
        return currentPlayerRoundScore;
    }

    public List<Player> getPlayers() {
        return dartgame.getPlayers();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerScore() {
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
            currentPlayerRoundScore += dartValueMapper.getDartScore(dart);
            currentRoundDarts.add(dart);
            notifications.publish(Notifications.DART_THROWN);
        }
    }
}
