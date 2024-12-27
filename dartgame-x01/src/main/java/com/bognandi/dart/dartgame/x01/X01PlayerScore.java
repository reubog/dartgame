package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.PlayerScore;

import java.util.*;
import java.util.Collections;


public class X01PlayerScore implements PlayerScore {

    private int score;
    private int initialScore;
    private int playedRounds;
    private List<Dart> thrownDarts = new ArrayList<>();
    private Map<Integer,List<Dart>> dartsForRound = new LinkedHashMap<>();


    public X01PlayerScore(int score) {
        this.initialScore = score;
        this.score = score;
    }

    public X01PlayerScore nextRound() {
        this.playedRounds++;
        dartsForRound.put(this.playedRounds, new ArrayList<>());
        return this;
    }

    public X01PlayerScore decreaseScoreWith(int minus) {
        this.score -= minus;
        return this;
    }

    public X01PlayerScore nextDart(Dart dart) {
        this.thrownDarts.add(dart);
        dartsForRound.get(this.playedRounds).add(dart);
        return this;
    }

    public X01PlayerScore setScore(int score) {
        this.score = score;
        return this;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getPlayedRounds() {
        return playedRounds;
    }

    @Override
    public List<Dart> getThrownDarts() {
        return thrownDarts;
    }

    @Override
    public List<Dart> getDartsForRound(int round) {
        return Collections.unmodifiableList(dartsForRound.get(round));
    }

    @Override
    public double getDartAverage() {
        return (initialScore - score) / thrownDarts.size() * 3;
    }

    @Override
    public double getPointsPerDart() {
        return (initialScore - score) / thrownDarts.size();
    }
}
