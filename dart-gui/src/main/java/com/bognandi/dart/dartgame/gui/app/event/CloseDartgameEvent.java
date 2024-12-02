package com.bognandi.dart.dartgame.gui.app.event;

import com.bognandi.dart.dartgame.gui.app.gui.GameController;
import org.springframework.context.ApplicationEvent;

public class CloseDartgameEvent extends ApplicationEvent {

    public CloseDartgameEvent(GameController controller) {
        super(controller);
    }
}