package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface ScoreBoard {
    int getMinimumNumberOfPlayers();
    boolean isBust(Player player);
    boolean isWinner(Player player);
    PlayerScore getPlayerScore(Player player);
    Player getLeadingPlayer();
    List<Player> getPlayerPosition();
}
