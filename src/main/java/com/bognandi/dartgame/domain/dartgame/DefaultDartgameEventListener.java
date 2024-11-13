package com.bognandi.dartgame.domain.dartgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDartgameEventListener implements DartgameEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDartgameEventListener.class);

    @Override
    public void onGameStarting(Dartgame dartGame) {
        LOG.debug("Game starting...");
    }

    @Override
    public void onGameStarted(Dartgame dartGame) {
        LOG.debug("Game started");
    }

    @Override
    public void onGameFinished(Dartgame dartGame) {
        LOG.debug("Game finished");
    }

    @Override
    public void onPlayerAdded(Dartgame dartGame, Player player) {
        LOG.debug("Player added: {}", player.getName());
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        LOG.debug("Round {} started", roundNumber);
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        LOG.debug("Player {}'s turn", player.getName());
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        LOG.debug("Dart thrown: {}", dart);
    }

    @Override
    public void onRemoveDarts(Dartgame dartGame, int round, Player player) {
        LOG.debug("Player {}'s darts removed", player.getName());
    }

    @Override
    public void onPlayerWon(Dartgame dartGame, Player player) {
        LOG.debug("Player {} won", player.getName());
    }

    @Override
    public void onPlayerBust(Dartgame dartGame, Player player) {
        LOG.debug("Player {} bust", player.getName());
    }

    @Override
    public void onPlayerLost(Dartgame dartGame, Player player) {
        LOG.debug("Player {} lost", player.getName());
    }
}
