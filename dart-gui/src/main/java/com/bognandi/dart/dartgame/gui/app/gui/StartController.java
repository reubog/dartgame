package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport;
import com.bognandi.dart.dartgame.gui.app.event.SplashScreenFinishedEvent;
import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import com.bognandi.dart.dartgame.gui.app.service.EventPublisherService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class StartController {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(StartController.class);

    @Autowired
    private EventPublisherService eventPublisherService;

    @EventListener(StageReadyEvent.class)
    public void onStageReadyEvent(StageReadyEvent event) {
        LOG.debug("Starting first gui ");
        Stage stage = event.getStage();
        stage.setScene(new Scene(JavaFxApplicationSupport.GAME_STARING_PARENT, 1920, 1080));
        stage.show();

        startTransitionToNextScene();
    }

    private void startTransitionToNextScene() {
        LOG.debug("Scheduling transition to next scene");
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        eventPublisherService.publish(new SplashScreenFinishedEvent(StartController.this));
                    }
                },
                Duration.ofSeconds(3).toMillis()
        );
    }
}
