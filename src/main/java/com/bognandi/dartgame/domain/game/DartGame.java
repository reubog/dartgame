package com.bognandi.dartgame.domain.game;

public interface DartGame {
    void addEventListener(DartGameEventListener listener);
    void removeEventListener(DartGameEventListener listener);
    void startGame(ScoreBoard scoreBoard);
    void addPlayer(Player player);

}
