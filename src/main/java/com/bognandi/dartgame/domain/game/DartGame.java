package com.bognandi.dartgame.domain.game;

public interface DartGame {
    void addEventListener(GameEventListener listener);
    void removeEventListener(GameEventListener listener);
    void startGame(DartBoardAction action, ScoreBoard scoreBoard);
    void addPlayer(Player player);

}
