package com.bognandi.dartgame.domain.dartgame;

public interface ScoreBoard {
    int getMinimumNumberOfPlayers();
    boolean isBust(Player player);
    boolean isWinner(Player player);
    PlayerScore getPlayerScore(Player player);
    Player getLeadingPlayer();
}
