package com.bognandi.dart.dartgame.gui.app;

import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaFxApplicationSupport extends Application {

    private static Logger LOG = LoggerFactory.getLogger(JavaFxApplicationSupport.class);

    public static Parent GAME_STARING_PARENT;
    public static Parent GAME_SELECTION_PARENT;
    public static Parent GAME_PARENT;


    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        LOG.info("Application initializing...");
        applicationContext = new SpringApplicationBuilder(JavaFxApplicationSupport.class).run(getParameters().getRaw().toArray(new String[0]));

        GAME_STARING_PARENT = loadFxml("/fxml/start.fxml");
        GAME_SELECTION_PARENT = loadFxml("/fxml/gameseletion.fxml");
        GAME_PARENT = loadFxml("/fxml/game.fxml");
    }

    private Parent loadFxml(String fxmlPath) {
        try {
            LOG.info("Loading fxml: {}", fxmlPath);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);
            return loader.load();
        } catch (Exception e) {
            LOG.error("Error loading fxml: {}", fxmlPath, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        LOG.info("Application starting...");
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/orange-juice/orange juice 2.0.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/Permanent_Marker/PermanentMarker-Regular.ttf"), 10);
        stage.setTitle("dart");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/fxml/images/icon.png")));
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
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
