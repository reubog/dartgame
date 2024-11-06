package com.bognandi.dartgame.app.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class StageReadyEvent extends ApplicationEvent {
    private ConfigurableApplicationContext applicationContext;

    public StageReadyEvent(ConfigurableApplicationContext applicationContext, Stage stage) {
        super(stage);
        this.applicationContext = applicationContext;
    }

    public ConfigurableApplicationContext getAppContext() {
        return applicationContext;
    }

    public Stage getStage() {
        return ((Stage) getSource());
    }
}