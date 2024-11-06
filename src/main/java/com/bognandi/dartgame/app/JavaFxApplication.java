package com.bognandi.dartgame.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFxApplication extends Application {

    private static Logger LOG = LoggerFactory.getLogger(JavaFxApplication.class);

    @Override
    public void start(Stage stage) throws Exception {
        LOG.info("Application starting...");
        // Load the FXML file and create the scene
//        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
//        Scene scene = new Scene(stage, 800, 600);
//        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        LOG.info("Application stopping...");
        Platform.exit();
    }
}
