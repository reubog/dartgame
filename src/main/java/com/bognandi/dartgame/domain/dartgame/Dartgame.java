package com.bognandi.dartgame.domain.dartgame;

import java.util.List;

public interface Dartgame {
    void addEventListener(DartgameEventListener listener);
    void removeEventListener(DartgameEventListener listener);
    void startGame();
    void addPlayer(Player player);
    List<Player> getPlayers();
    PlayerScore getPlayerScore(Player player);
    ScoreBoard  getScoreBoard();
}
