package com.bognandi.dartgame.app.stage;

import com.bognandi.dartgame.app.event.StageReadyEvent;
import com.bognandi.dartgame.app.service.GameService;
import com.bognandi.dartgame.app.service.audio.AudioService;
import com.bognandi.dartgame.app.service.audio.SoundClip;
import com.bognandi.dartgame.app.service.speech.SpeechService;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(StageInitializer.class);

    @Value("classpath:fxml/main.fxml")
    private Resource quizResource;

    @Autowired
    private AudioService audioService;

    @Autowired
    private SpeechService speechService;

    @Autowired
    private GameService gameService;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle) {
        this.applicationTitle = applicationTitle;
        LOG.debug("First stage initialized");
    }

    private String applicationTitle;
    private ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        LOG.debug("Setting up first stage...");
        Stage stage = event.getStage();
        ConfigurableApplicationContext springContext = event.getAppContext();
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(quizResource.getURL());
//            fxmlLoader.setControllerFactory(springContext::getBean);
//            Parent parent = fxmlLoader.load();
//            stage.setScene(new Scene(parent, 800, 600));
        stage.setTitle(applicationTitle);
        // create a button
        Button single = new Button("Single");
        single.setOnAction((actionEvent -> {
            LOG.debug("button pressed");
            executorService.submit(() -> audioService.playAudioClip(SoundClip.SINGLE));
            executorService.submit(() -> speechService.doSpeak("single"));
        }));

        Button dubbel = new Button("Double");
        dubbel.setOnAction((actionEvent -> {
            LOG.debug("button pressed");
            executorService.submit(() -> audioService.playAudioClip(SoundClip.DOUBLE));
            executorService.submit(() -> speechService.doSpeak("double"));
        }));

        Button trippel = new Button("Triple");
        trippel.setOnAction((actionEvent -> {
            LOG.debug("button pressed");
            executorService.submit(() -> audioService.playAudioClip(SoundClip.TRIPLE));
            executorService.submit(() -> speechService.doSpeak("triple"));
        }));

        Button game301 = new Button("Play Game 301");
        game301.setOnAction((actionEvent -> {
            LOG.debug("Play 301 pressed");
            executorService.submit(() -> gameService.playGame("301"));
        }));

        // create a stack pane
        VBox r = new VBox();

        // add button
        r.getChildren().add(single);
        r.getChildren().add(dubbel);
        r.getChildren().add(trippel);
        r.getChildren().add(game301);

        // create a scene
        Scene sc = new Scene(r, 200, 200);

        // set the scene
        stage.setScene(sc);
        stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
    }
}
