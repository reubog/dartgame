package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.DartgameDescriptor;
import com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport;
import com.bognandi.dart.dartgame.gui.app.event.CloseScoreboardEvent;
import com.bognandi.dart.dartgame.gui.app.event.SplashScreenFinishedEvent;
import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import com.bognandi.dart.dartgame.gui.app.event.StartDartgameEvent;
import com.bognandi.dart.dartgame.gui.app.service.DartgamesService;
import com.bognandi.dart.dartgame.gui.app.service.dartboard.DartboardService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class GameSelectionController {

    private static final Logger LOG = LoggerFactory.getLogger(GameSelectionController.class);

    @FXML
    private Button selectButton;

    @FXML
    private ListView<DartgameDescriptor> gamesList;

    @Autowired
    private DartgamesService dartgamesService;

    @Autowired
    private DartboardService dartboardService;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private Parent parent;
    private Stage stage;

    public GameSelectionController() {
        LOG.debug("Creating game selection controller");
    }

    @EventListener(StageReadyEvent.class)
    public void saveStage(StageReadyEvent event) {
        LOG.debug("Saving stage");
        this.stage = event.getStage();
    }

    @EventListener(SplashScreenFinishedEvent.class)
    public void startGameSelection(SplashScreenFinishedEvent event) {
        startGui();
    }

    @EventListener(CloseScoreboardEvent.class)
    public void onGameEnded(CloseScoreboardEvent event) {
        startGui();
    }

    private void startGui() {
        platformRun(() -> {
            LOG.debug("Starting game selection gui");
            stage.getScene().setRoot(JavaFxApplicationSupport.GAME_SELECTION_PARENT);
            setupDartboardEvents();
        });
    }

    @FXML
    public void initialize() {
        LOG.debug("Initializing game selection gui");
        gamesList.setCellFactory(listView -> new GameDescriptorListCell());
        gamesList.getItems().addAll(dartgamesService.getDartgames());

        if (gamesList.getItems().size() > 0) {
            gamesList.getSelectionModel().select(0);
        }
    }

    private void setupDartboardEvents() {
        dartboardService.getDartboard().setOnDartboardValue(dartboardValue -> {
            switch (dartboardValue) {
                case RED_BUTTON:
                    startGame();
                    break;

                case TWENTY_INNER:
                case TRIPLE_TWENTY:
                case DOUBLE_TWENTY:
                case TWENTY_OUTER:
                    selectPrevious();
                    break;

                case THREE_INNER:
                case THREE_OUTER:
                case TRIPLE_THREE:
                case DOUBLE_THREE:
                    selectNext();
                    break;

                case ELEVEN_INNER:
                case ELEVEN_OUTER:
                case DOUBLE_ELEVEN:
                case TRIPLE_ELEVEN:
                    Platform.exit();
                    break;
            }
        });
    }

    @FXML
    public void selectPrevious() {
        int selectedIndex = gamesList.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            gamesList.getSelectionModel().selectPrevious();
        }
    }

    @FXML
    public void selectNext() {
        int selectedIndex = gamesList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < gamesList.getItems().size() - 1) {
            gamesList.getSelectionModel().selectNext();
        }
    }

    @FXML
    public void startGame() {
        DartgameDescriptor selectedGame = gamesList.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            LOG.warn("No game selected");
            return;
        }

        applicationContext.publishEvent(new StartDartgameEvent(selectedGame, stage));
    }

    private void platformRun(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            //LOG.debug("Already on JavaFX thread");
            runnable.run();
        } else {
            //LOG.debug("Running on non-JavaFX thread, so scheduling...");
            Platform.runLater(runnable);
        }
    }
}
