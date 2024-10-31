package com.bognandi.dartgame.domain.game;

public interface DartGame {
    void initGame(DartBoardAction action, Notifyer notifyer);
    void startGame();
    void addPlayer(Player player);

}
