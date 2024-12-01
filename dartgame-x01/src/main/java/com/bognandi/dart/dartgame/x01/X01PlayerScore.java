package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.PlayerScore;

import java.util.*;
import java.util.Collections;


public class X01PlayerScore implements PlayerScore {

    private int score;
    private int playedRounds;
    private int thrownDarts;
    private Map<Integer,List<Dart>> dartsForRound = new LinkedHashMap<>();

    public X01PlayerScore(int score) {
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
        this.thrownDarts++;
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
    public int getThrownDarts() {
        return thrownDarts;
    }

    @Override
    public List<Dart> getDartsForRound(int round) {
        return Collections.unmodifiableList(dartsForRound.get(round));
    }
}
