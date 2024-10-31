package com.bognandi.dartgame.domain.game;

public interface ScoreBoard {
    boolean isBust(Player player);
    boolean isWinner(Player player);
}
