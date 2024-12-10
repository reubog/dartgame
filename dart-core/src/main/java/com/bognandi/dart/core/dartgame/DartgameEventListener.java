package com.bognandi.dart.core.dartgame;

import java.util.List;

public interface DartgameEventListener {
    //void onWaitingForPlayers(Dartgame dartGame);
    void onGamePlayStarted(Dartgame dartGame);
    void onGameFinished(Dartgame dartGame);

    void onPlayersSet(Dartgame dartGame, List<Player> players);

    void onRoundStarted(Dartgame dartGame, int roundNumber);
    void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player);

    void onDartThrown(Dartgame dartGame, Dart dart);
    void onRemoveDarts(Dartgame dartGame, int round, Player player);

    void onPlayerWon(Dartgame dartGame, Player player);
    void onPlayerBust(Dartgame dartGame, Player player);
    void onPlayerLost(Dartgame dartGame, Player player);
}
