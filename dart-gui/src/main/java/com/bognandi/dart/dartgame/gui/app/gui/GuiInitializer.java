package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GuiInitializer implements ApplicationListener<StageReadyEvent> {

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            loader.setControllerFactory(event.getAppContext()::getBean);

            Parent root = loader.load();

            Stage stage = event.getStage();
            stage.setScene(new Scene(root, 1920, 1080));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
