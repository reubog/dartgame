package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface Dartgame {
    void attachDartboard(Dartboard dartboard);
    void addEventListener(DartgameEventListener listener);
    void removeEventListener(DartgameEventListener listener);
    void startPlaying();
    void setPlayers(List<Player> players);
    List<Player> getPlayers();
    PlayerScore getPlayerScore(Player player);
    ScoreBoard  getScoreBoard();
}
