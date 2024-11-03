package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.dartgame.PlayerScore;


public class X01PlayerScore implements PlayerScore {

    private int score;
    private int playedRounds;
    private int thrownDarts;

    public X01PlayerScore(int score) {
        this.score = score;
    }

    public X01PlayerScore nextRound() {
        this.playedRounds++;
        return this;
    }

    public X01PlayerScore decreaseScoreWith(int minus) {
        this.score -= minus;
        return this;
    }

    public X01PlayerScore nextDart() {
        this.thrownDarts++;
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
}
