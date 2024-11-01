package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class X01ScoreBoard implements ScoreBoard, DartGameEventListener {

    private final int startScore;
    private List<PlayerRound> playerRounds;
    private Map<Player,PlayerScore> playerScoreMap;


    @Override
    public int getMinimumNumberOfPlayers() {
        return 0;
    }

    @Override
    public boolean isBust(Player player) {
        return false;
    }

    @Override
    public boolean isWinner(Player player) {
        return false;
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

    class PlayerRound {

    }

}
