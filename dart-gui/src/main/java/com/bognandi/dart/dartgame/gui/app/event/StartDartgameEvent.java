package com.bognandi.dart.dartgame.gui.app.event;

import com.bognandi.dart.core.dartgame.DartgameDescriptor;
import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StartDartgameEvent extends ApplicationEvent {
    private DartgameDescriptor dartgameDescriptor;

    public StartDartgameEvent(DartgameDescriptor dartgameDescriptor, Stage stage) {
        super(stage);
        this.dartgameDescriptor = dartgameDescriptor;
    }

    public DartgameDescriptor getDartgameDescriptor() {
        return dartgameDescriptor;
    }

    public Stage getStage() {
        return ((Stage) getSource());
    }
}