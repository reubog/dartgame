package com.bognandi.dart.dartgame.gui.app.event;

import com.bognandi.dart.dartgame.gui.app.gui.GameController;
import org.springframework.context.ApplicationEvent;

public class EndedDartgameEvent extends ApplicationEvent {

    public EndedDartgameEvent(GameController controller) {
        super(controller);
    }
}