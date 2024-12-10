package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.Dartgame;
import com.bognandi.dart.core.dartgame.DartgameEventListener;
import com.bognandi.dart.core.dartgame.Player;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlatformDartgameListenerWrapper implements DartgameEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformDartgameListenerWrapper.class);

    private DartgameEventListener delegate;

    public PlatformDartgameListenerWrapper(DartgameEventListener delegate) {
        this.delegate = delegate;
    }

//    public void onWaitingForPlayers(Dartgame dartGame) {
//        platformRun(() -> delegate.onWaitingForPlayers(dartGame));
//    }

    @Override
    public void onGamePlayStarted(Dartgame dartGame) {
        platformRun(() -> delegate.onGamePlayStarted(dartGame));
    }

    @Override
    public void onGameFinished(Dartgame dartGame) {
        platformRun(() -> delegate.onGameFinished(dartGame));
    }

    @Override
    public void onPlayerAdded(Dartgame dartGame, Player player) {
        platformRun(() -> delegate.onPlayerAdded(dartGame, player));
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        platformRun(() -> delegate.onRoundStarted(dartGame, roundNumber));
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        platformRun(() -> delegate.onPlayerTurn(dartGame, roundNumber, player));
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        platformRun(() -> delegate.onDartThrown(dartGame, dart));
    }

    @Override
    public void onRemoveDarts(Dartgame dartGame, int round, Player player) {
        platformRun(() -> delegate.onRemoveDarts(dartGame, round, player));
    }

    @Override
    public void onPlayerWon(Dartgame dartGame, Player player) {
        platformRun(() -> delegate.onPlayerWon(dartGame, player));
    }

    @Override
    public void onPlayerBust(Dartgame dartGame, Player player) {
        platformRun(() -> delegate.onPlayerBust(dartGame, player));
    }

    @Override
    public void onPlayerLost(Dartgame dartGame, Player player) {
        platformRun(() -> delegate.onPlayerLost(dartGame, player));
    }

    private void platformRun(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            LOG.debug("Already on JavaFX thread");
            runnable.run();
        } else {
            LOG.debug("Running on non-JavaFX thread");
            Platform.runLater(runnable);
        }
    }
}
