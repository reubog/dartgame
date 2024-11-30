package com.bognandi.dart.dartboard.mqtt.gui.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GuiInitializer implements ApplicationListener<StageReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(GuiInitializer.class);

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            LOG.debug("GUI initializing...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui.fxml"));
            loader.setControllerFactory(event.getAppContext()::getBean);

            Parent root = loader.load();

            Stage stage = event.getStage();
            stage.setScene(new Scene(root,400,200));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
