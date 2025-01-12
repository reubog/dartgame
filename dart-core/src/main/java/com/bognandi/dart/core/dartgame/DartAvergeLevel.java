package com.bognandi.dart.core.dartgame;

import java.util.Arrays;
import java.util.Comparator;

public enum DartAvergeLevel {
    FIRST_TIMER(0),
    BEGINNER(35),
    PUB_PLAYER(46),
    LEAGUE_PLAYER(56),
    PDC_CHALLENGE_TOUR(71),
    PDC_TOUR_PLAYER(86),
    WORLD_CLASS(100);

    private final int averageThreshold;

    DartAvergeLevel(int average) {
        this.averageThreshold = average;
    }

    public int getAverageThreshold() {
        return averageThreshold;
    }

    public static DartAvergeLevel findLevel(int average) {
        return Arrays.stream(DartAvergeLevel.values())
                .filter(level -> average >= level.getAverageThreshold())
                .sorted(Comparator.comparing(DartAvergeLevel::getAverageThreshold).reversed())
                .findFirst()
                .orElse(DartAvergeLevel.FIRST_TIMER);
    }

}
