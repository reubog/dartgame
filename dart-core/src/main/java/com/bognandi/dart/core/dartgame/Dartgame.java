package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface Dartgame {
    void attachDartboard(Dartboard dartboard);
    void addEventListener(DartgameEventListener listener);
    void removeEventListener(DartgameEventListener listener);
    void initGameWaitForPlayers();
    void startPlaying();
    void addPlayer(Player player);
    List<Player> getPlayers();
    PlayerScore getPlayerScore(Player player);
    ScoreBoard  getScoreBoard();
}
