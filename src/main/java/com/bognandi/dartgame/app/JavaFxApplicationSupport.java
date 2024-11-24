package com.bognandi.dartgame.app;

import com.bognandi.dartgame.app.event.StageReadyEvent;
import com.bognandi.dartgame.app.gui.WrapperLayoutBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaFxApplicationSupport extends Application {

    private static Logger LOG = LoggerFactory.getLogger(JavaFxApplicationSupport.class);

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        LOG.info("Application initializing...");
        applicationContext = new SpringApplicationBuilder(JavaFxApplicationSupport.class).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        LOG.info("Application starting...");
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/orange-juice/orange juice 2.0.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/Permanent_Marker/PermanentMarker-Regular.ttf"), 10);
        applicationContext.publishEvent(new StageReadyEvent(applicationContext, stage));
    }

    @Override
    public void stop() {
        LOG.info("Application stopping...");
        applicationContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
