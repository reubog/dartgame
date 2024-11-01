package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class X01PlayerScore implements PlayerScore, DartGameEventListener {

    private final static Logger LOG = LoggerFactory.getLogger(X01PlayerScore.class);

    private class DartStatus {
        Dart dart;
        boolean committed;

        public DartStatus(Dart dart, boolean committed) {
            this.dart = dart;
            this.committed = committed;
        }
    }

    private final int startScore;
    private final DartValueMapper dartValueMapper;
    private int playedRounds;
    private boolean enableDarts;
    private List<DartStatus> thrownDarts = new ArrayList<>();
    private int currentScore;


    @Override
    public int getTotalScore() {
        return currentScore;
    }

    @Override
    public int getPlayedRounds() {
        return playedRounds;
    }

    @Override
    public int getThrownDarts() {
        return thrownDarts.size();
    }

    @Override
    public void onGameStarting(DartGame dartGame) {
        ;
    }

    @Override
    public void onGameStarted(DartGame dartGame) {
        enableDarts = false;
        thrownDarts.clear();
        currentScore = startScore;
    }

    @Override
    public void onGameFinished(DartGame dartGame) {
        ;
    }

    @Override
    public void onPlayerAdded(DartGame dartGame, Player player) {
        ;
    }

    @Override
    public void onRoundStarted(DartGame dartGame, int roundNumber) {
        roundNumber++;
    }

    @Override
    public void onPlayerTurn(DartGame dartGame, int roundNumber, Player player) {
        if (this != player) {
            return;
        }
        enableDarts = true;
    }

    @Override
    public void onDartThrown(DartGame dartGame, Dart dart) {
        if (!enableDarts) {
            return;
        }
        thrownDarts.add(new DartStatus(dart,false));
        calculateScore();
    }

    @Override
    public void onRemoveDarts(DartGame dartGame, int round, Player player) {
        if (this != player) {
            return;
        }

        enableDarts = false;
        thrownDarts.stream()
                .filter(dartStatus -> !dartStatus.committed)
                .forEach(dartStatus -> dartStatus.committed = true);
        calculateScore();
    }

    @Override
    public void onPlayerWon(DartGame dartGame, Player player) {
        if (this != player) {
            return;
        }

        enableDarts = false;
        thrownDarts.stream()
                .filter(dartStatus -> !dartStatus.committed)
                .forEach(dartStatus -> dartStatus.committed = true);
        calculateScore();
    }

    @Override
    public void onPlayerBust(DartGame dartGame, Player player) {
        if (this != player) {
            return;
        }

        enableDarts = false;
        thrownDarts.removeAll(thrownDarts.stream()
                .filter(dartStatus -> !dartStatus.committed)
                .toList());
        calculateScore();
    }

    private void calculateScore() {
        currentScore -= thrownDarts.stream()
                .map(dartStatus -> dartValueMapper.getDartValue(dartStatus.dart))
                .mapToInt(Integer::intValue)
                .sum();
        LOG.info("Current score is {}", currentScore);
    }


}
