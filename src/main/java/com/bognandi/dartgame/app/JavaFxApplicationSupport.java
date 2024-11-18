package com.bognandi.dartgame.app;

import com.bognandi.dartgame.app.gui.WrapperLayoutBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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
        applicationContext = new SpringApplicationBuilder(JavaFxApplicationSupport.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        LOG.info("Application starting...");
        stage.setScene(new Scene(new WrapperLayoutBuilder().build()));
        stage.show();

    }

    @Override
    public void stop() {
        LOG.info("Application stopping...");
        applicationContext.close();
        Platform.exit();
    }
}
