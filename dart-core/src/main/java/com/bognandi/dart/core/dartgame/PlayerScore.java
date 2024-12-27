package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface PlayerScore {
    int getScore();
    int getPlayedRounds();
    List<Dart> getThrownDarts();
    List<Dart> getDartsForRound(int round);
    double getDartAverage();
    double getPointsPerDart();
}
