package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConnectDartboardController {

    private Stage stage;

    @EventListener(StageReadyEvent.class)
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }
}
