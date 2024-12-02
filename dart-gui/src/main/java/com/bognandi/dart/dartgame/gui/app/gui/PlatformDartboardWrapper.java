package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartgame.Dartboard;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class PlatformDartboardWrapper implements Dartboard {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformDartboardWrapper.class);

    private Dartboard delegate;

    public PlatformDartboardWrapper(Dartboard delegate) {
        this.delegate = delegate;
    }

    @Override
    public DartboardStatus getStatus() {
        return delegate.getStatus();
    }

    @Override
    public void setOnStatusChange(Consumer<DartboardStatus> statusConsumer) {
        platformRun(() -> delegate.setOnStatusChange(status -> statusConsumer.accept(status)));
    }

    @Override
    public void setOnDartboardValue(Consumer<DartboardValue> valueConsumer) {
        platformRun(() -> delegate.setOnDartboardValue(value -> valueConsumer.accept(value)));
    }

    private void platformRun(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            LOG.debug("Running on JavaFX thread");
            runnable.run();
        } else {
            LOG.debug("Running on non-JavaFX thread");
            Platform.runLater(runnable);
        }
    }
}