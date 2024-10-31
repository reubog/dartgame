package com.bognandi.dartgame.domain.game;

public interface DartGame {
    void initGame(DartBoardAction action, ScoreBoard scoreBoard, DartGameNotification... dartGameNotifications);
    void startGame();
    void addPlayer(Player player);

}
