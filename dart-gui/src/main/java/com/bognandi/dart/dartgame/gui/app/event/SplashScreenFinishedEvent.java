package com.bognandi.dart.dartgame.gui.app.event;

import org.springframework.context.ApplicationEvent;

public class SplashScreenFinishedEvent extends ApplicationEvent {

    public SplashScreenFinishedEvent(Object source) {
        super(source);
    }
}