package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface PlayerScore {
    int getScore();
    int getPlayedRounds();
    int getThrownDarts();
    List<Dart> getDartsForRound(int round);
}
