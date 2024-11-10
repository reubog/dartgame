package com.bognandi.dartgame.domain.dartgame;

public interface DartGame {
    String getName();
    void addEventListener(DartGameEventListener listener);
    void removeEventListener(DartGameEventListener listener);
    void startGame(ScoreBoard scoreBoard);
    void addPlayer(Player player);
    PlayerScore getPlayerScore(Player player);
}
