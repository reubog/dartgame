package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.dartgame.*;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class X01ScoreBoard implements ScoreBoard, DartgameEventListener {

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
    public void onGameStarting(Dartgame dartGame) {

    }

    @Override
    public void onGameStarted(Dartgame dartGame) {

    }

    @Override
    public void onGameFinished(Dartgame dartGame) {

    }

    @Override
    public void onPlayerAdded(Dartgame dartGame, Player player) {
        playerScoreMap.put(player, new X01PlayerScore(startScore));
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        playerScoreMap.values().forEach(X01PlayerScore::nextRound);
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        currentPlayer = player;
        currentPlayerTurnStartScore = playerScoreMap.get(currentPlayer).getScore();
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        playerScoreMap.get(currentPlayer)
                .decreaseScoreWith(dartValueMapper.getDartValue(dart))
                .nextDart();
    }

    @Override
    public void onRemoveDarts(Dartgame dartGame, int round, Player player) {

    }

    @Override
    public void onPlayerWon(Dartgame dartGame, Player player) {

    }

    @Override
    public void onPlayerBust(Dartgame dartGame, Player player) {
        playerScoreMap.get(currentPlayer).setScore(currentPlayerTurnStartScore);
    }

    @Override
    public void onPlayerLost(Dartgame dartGame, Player player) {

    }

}
