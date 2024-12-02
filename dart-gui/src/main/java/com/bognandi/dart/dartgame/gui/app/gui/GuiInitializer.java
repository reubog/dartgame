package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.dartgame.gui.app.event.SplashScreenFinishedEvent;
import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class GuiInitializer {

    //@EventListener(StageReadyEvent.class)
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/st.fxml"));
            loader.setControllerFactory(event.getAppContext()::getBean);

            Parent root = loader.load();

            Scene scene = new Scene(root, 1920, 1080);

            Stage stage = event.getStage();
            stage.setScene(scene);
            stage.show();

            Timer timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            event.getAppContext().publishEvent(new SplashScreenFinishedEvent(GuiInitializer.this));
                        }
                    },
                    Duration.ofSeconds(3).toMillis()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
