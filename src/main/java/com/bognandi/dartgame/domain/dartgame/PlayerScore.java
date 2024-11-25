package com.bognandi.dartgame.domain.dartgame;

import java.util.List;

public interface PlayerScore {
    int getScore();
    int getPlayedRounds();
    int getThrownDarts();
    List<Dart> getDartsForRound(int round);
}
