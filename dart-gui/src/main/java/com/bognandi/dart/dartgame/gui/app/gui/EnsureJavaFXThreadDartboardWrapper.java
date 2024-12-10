package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartboard.DartboardStatus;
import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartgame.Dartboard;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class EnsureJavaFXThreadDartboardWrapper implements Dartboard {

    private static final Logger LOG = LoggerFactory.getLogger(EnsureJavaFXThreadDartboardWrapper.class);

    private Dartboard delegate;

    public EnsureJavaFXThreadDartboardWrapper(Dartboard delegate) {
        this.delegate = delegate;
    }

    @Override
    public DartboardStatus getStatus() {
        return delegate.getStatus();
    }

    @Override
    public void setOnStatusChange(Consumer<DartboardStatus> statusConsumer) {
        delegate.setOnStatusChange(status -> platformRun(() -> {
            LOG.debug("Status changed to {}", status);
            statusConsumer.accept(status);
        }));
    }

    @Override
    public void setOnDartboardValue(Consumer<DartboardValue> valueConsumer) {
        delegate.setOnDartboardValue(value -> platformRun(() -> {
            LOG.debug("Value changed to {}", value);
            valueConsumer.accept(value);
        }));
    }

    private void platformRun(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            //LOG.debug("Already on JavaFX thread");
            runnable.run();
        } else {
            //LOG.debug("Running on non-JavaFX thread, so scheduling...");
            Platform.runLater(runnable);
        }
    }
}
