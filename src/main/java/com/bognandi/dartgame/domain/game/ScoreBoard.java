package com.bognandi.dartgame.domain.game;

public interface ScoreBoard {
    int getMinimumNumberOfPlayers();
    boolean isBust(Player player);
    boolean isWinner(Player player);
}
