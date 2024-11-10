package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.dartgame.*;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class X01ScoreBoard implements ScoreBoard, DartGameEventListener {

    private final int startScore;
    private int currentPlayerTurnStartScore;
    private Player currentPlayer;
    private final DartValueMapper dartValueMapper;
    //private List<PlayerRound> playerRounds;
    private Map<Player, X01PlayerScore> playerScoreMap = new LinkedHashMap<>();

    @Override
    public int getMinimumNumberOfPlayers() {
        return 2;
    }

    @Override
    public boolean isBust(Player player) {
        return playerScoreMap.get(currentPlayer).getScore() < 0;
    }

    @Override
    public boolean isWinner(Player player) {
        return playerScoreMap.get(currentPlayer).getScore() == 0;
    }

    @Override
    public PlayerScore getPlayerScore(Player player) {
        return playerScoreMap.get(player);
    }

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
        playerScoreMap.put(player, new X01PlayerScore(startScore));
    }

    @Override
    public void onRoundStarted(DartGame dartGame, int roundNumber) {
        playerScoreMap.values().forEach(X01PlayerScore::nextRound);
    }

    @Override
    public void onPlayerTurn(DartGame dartGame, int roundNumber, Player player) {
        currentPlayer = player;
        currentPlayerTurnStartScore = playerScoreMap.get(currentPlayer).getScore();
    }

    @Override
    public void onDartThrown(DartGame dartGame, Dart dart) {
        playerScoreMap.get(currentPlayer)
                .decreaseScoreWith(dartValueMapper.getDartValue(dart))
                .nextDart();
    }

    @Override
    public void onRemoveDarts(DartGame dartGame, int round, Player player) {

    }

    @Override
    public void onPlayerWon(DartGame dartGame, Player player) {

    }

    @Override
    public void onPlayerBust(DartGame dartGame, Player player) {
        playerScoreMap.get(currentPlayer).setScore(currentPlayerTurnStartScore);
    }

    @Override
    public void onPlayerLost(DartGame dartGame, Player player) {

    }

}
