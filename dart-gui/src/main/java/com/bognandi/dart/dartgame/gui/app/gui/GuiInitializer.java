package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GuiInitializer {

    @EventListener(StageReadyEvent.class)
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameseletion.fxml"));
            loader.setControllerFactory(event.getAppContext()::getBean);

            Parent root = loader.load();

            Scene scene = new Scene(root, 1920, 1080);

            Stage stage = event.getStage();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
