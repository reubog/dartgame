package com.bognandi.dartgame.domain.dartgame;

public interface DartgameEventListener {
    void onGameStarting(Dartgame dartGame);
    void onGameStarted(Dartgame dartGame);
    void onGameFinished(Dartgame dartGame);

    void onPlayerAdded(Dartgame dartGame, Player player);

    void onRoundStarted(Dartgame dartGame, int roundNumber);
    void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player);

    void onDartThrown(Dartgame dartGame, Dart dart);
    void onRemoveDarts(Dartgame dartGame, int round, Player player);

    void onPlayerWon(Dartgame dartGame, Player player);
    void onPlayerBust(Dartgame dartGame, Player player);
    void onPlayerLost(Dartgame dartGame, Player player);
}
