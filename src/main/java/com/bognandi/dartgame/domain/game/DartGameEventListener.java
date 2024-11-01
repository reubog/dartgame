package com.bognandi.dartgame.domain.game;

public interface DartGameEventListener {
    void onGameStarting(DartGame dartGame);
    void onGameStarted(DartGame dartGame);
    void onGameFinished(DartGame dartGame);

    void onPlayerAdded(DartGame dartGame, Player player);

    void onRoundStarted(DartGame dartGame, int roundNumber);
    void onPlayerTurn(DartGame dartGame, int roundNumber, Player player);

    void onDartThrown(DartGame dartGame, Dart dart);
    void onRemoveDarts(DartGame dartGame, int round, Player player);

    void onPlayerWon(DartGame dartGame, Player player);
    void onPlayerBust(DartGame dartGame, Player player);
}
