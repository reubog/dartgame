package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.Dart;
import com.bognandi.dartgame.domain.game.PlayerScore;

public class X01PlayerScore implements PlayerScore {
    private int totalScore;
    private int playedRounds;
    private int thrownDarts;

    private int currentScore;

    public X01PlayerScore(int startScore) {
        this.currentScore = startScore;
    }

    public void addThrownDart(Dart dart) {
        currentScore -= mapDartToScore(dart);
        thrownDarts++;
    }

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
        return thrownDarts;
    }

    private int mapDartToScore(Dart dart) {
        switch (dart) {
            case DOUBLE_BULLSEYE:
                return 50;
            case BULLSEYE:
                return 25;
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case ELEVEN:
                return 11;
            case TWELVE:
                return 12;
            case THIRTEEN:
                return 13;
            case FOURTEEN:
                return 14;
            case FIFTEEN:
                return 15;
            case SIXTEEN:
                return 16;
            case SEVENTEEN:
                return 17;
            case EIGHTEEN:
                return 18;
            case NINETEEN:
                return 19;
            case TWENTY:
                return 20;
            case DOUBLE_ONE:
                return 2;
            case DOUBLE_TWO:
                return 4;
            case DOUBLE_THREE:
                return 6;
            case DOUBLE_FOUR:
                return 8;
            case DOUBLE_FIVE:
                return 10;
            case DOUBLE_SIX:
                return 12;
            case DOUBLE_SEVEN:
                return 14;
            case DOUBLE_EIGHT:
                return 16;
            case DOUBLE_NINE:
                return 18;
            case DOUBLE_TEN:
                return 20;
            case DOUBLE_ELEVEN:
                return 22;
            case DOUBLE_TWELVE:
                return 24;
            case DOUBLE_THIRTEEN:
                return 26;
            case DOUBLE_FOURTEEN:
                return 28;
            case DOUBLE_FIFTEEN:
                return 30;
            case DOUBLE_SIXTEEN:
                return 32;
            case DOUBLE_SEVENTEEN:
                return 34;
            case DOUBLE_EIGHTEEN:
                return 36;
            case DOUBLE_NINETEEN:
                return 38;
            case DOUBLE_TWENTY:
                return 40;
            case TRIPLE_ONE:
                return 3;
            case TRIPLE_TWO:
                return 6;
            case TRIPLE_THREE:
                return 9;
            case TRIPLE_FOUR:
                return 12;
            case TRIPLE_FIVE:
                return 15;
            case TRIPLE_SIX:
                return 18;
            case TRIPLE_SEVEN:
                return 21;
            case TRIPLE_EIGHT:
                return 24;
            case TRIPLE_NINE:
                return 27;
            case TRIPLE_TEN:
                return 30;
            case TRIPLE_ELEVEN:
                return 33;
            case TRIPLE_TWELVE:
                return 36;
            case TRIPLE_THIRTEEN:
                return 39;
            case TRIPLE_FOURTEEN:
                return 42;
            case TRIPLE_FIFTEEN:
                return 45;
            case TRIPLE_SIXTEEN:
                return 48;
            case TRIPLE_SEVENTEEN:
                return 51;
            case TRIPLE_EIGHTEEN:
                return 54;
            case TRIPLE_NINETEEN:
                return 57;
            case TRIPLE_TWENTY:
                return 60;
            default:
                return 0;
        }
    }
}
