package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.*;
import com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport;
import com.bognandi.dart.dartgame.gui.app.event.EndedDartgameEvent;
import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import com.bognandi.dart.dartgame.gui.app.event.StartDartgameEvent;
import com.bognandi.dart.dartgame.gui.app.service.DartgamesService;
import com.bognandi.dart.dartgame.gui.app.service.EventPublisherService;
import com.bognandi.dart.dartgame.gui.app.service.dartboard.DartboardService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameController extends DefaultDartgameEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private static final String BACKGROUND_VIDEO_RESOURCE = "/video/tavern.mp4";
    private static final String SCORETABLEKEY_ROUND = "round";

    @Autowired
    private DartValueMapper dartValueMapper;

    @Autowired
    private DartboardService dartboardService;

    @Autowired
    private DartgamesService dartgamesService;

    @Autowired
    private EventPublisherService  eventPublisherService;

    @FXML
    private MediaView backgroundMediaView;

    @FXML
    private AnchorPane messagePane;

    @FXML
    private Label messageLabel;

    @FXML
    private BorderPane gamePane;

    @FXML
    private TableView<Map<String, Object>> scoreTable;

    @FXML
    private ListView<Dart> dartsList;

    @FXML
    private ListView<GamePlayer> playersList;

    @FXML
    private Label currentPlayerScoreLabel;

    private Dartgame dartgame;
    private Player currentPlayer;
    private int currentRound;
    private ObservableList<Map<String, Object>> scoreData = FXCollections.observableArrayList();
    private Map<String, Object> currentScoreRound;
    private MediaPlayer mediaPlayer;
    private int guestPlayers = 0;
    private ConfigurableApplicationContext context;

    public record GamePlayer(Player player, PlayerScore playerScore, AtomicBoolean leaderScore) {
    }

    public record GameRound(int round, Map<Player, AtomicInteger> score) {
    }

    private Map<Player, GamePlayer> gamePlayerMap = new LinkedHashMap<>();
    private Parent parent;
    private Stage stage;

    public GameController(@Autowired ConfigurableApplicationContext context) {
        this.context = context;
        LOG.debug("GameController created");
    }

    @FXML
    public void initialize() {
        LOG.debug("Initializing game controller view");
        playersList.setCellFactory(listView -> new GamePlayerListCell());
        dartsList.setCellFactory(listView -> new DartListCell());

        TableColumn column = new TableColumn<GameRound, String>("Round");
        column.setCellValueFactory(new MapValueFactory<>(SCORETABLEKEY_ROUND));
        column.setPrefWidth(100.0); // TODO why this remove? control from fxml

        scoreTable.getColumns().add(column);
        scoreTable.setPrefWidth(column.getPrefWidth() + 1);
        scoreTable.setItems(scoreData);

        Media media = new Media(getClass().getResource(BACKGROUND_VIDEO_RESOURCE).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        backgroundMediaView.setMediaPlayer(mediaPlayer);

        currentPlayerScoreLabel.setVisible(false);
    }

    @EventListener(StageReadyEvent.class)
    public void savestage(StageReadyEvent event) {
        LOG.debug("Stage ready event received");
        stage = event.getStage();
    }

    @EventListener(StartDartgameEvent.class)
    public void startGame(StartDartgameEvent event) {
        LOG.debug("Start dartgame event received, showing view");
        platformRun(() -> {
            stage.getScene().setRoot(JavaFxApplicationSupport.GAME_PARENT);

            dartgame = dartgamesService.createDartgame(event.getDartgameDescriptor());
            dartgame.addEventListener(new PlatformDartgameListenerWrapper(this));
            dartgame.initGameWaitForPlayers();
        });
    }

    @Override
    public void onWaitingForPlayers(Dartgame dartGame) {
        Platform.runLater(() -> {
            showMessage("Waiting for players...");

            dartboardService.getDartboard().setOnDartboardValue(dartboardValue -> platformRun(() -> {
                LOG.debug("Dartboard value: {}", dartboardValue);
                switch (dartboardValue) {
                    case RED_BUTTON:
                        IntStream.range(1, guestPlayers + 1)
                                .forEach(i -> dartgame.addPlayer(new DefaultPlayer("Guest " + i)));
                        dartgame.startPlaying();
                        break;

                    case TWO_INNER:
                    case TWO_OUTER:
                    case TRIPLE_TWO:
                    case DOUBLE_TWO:
                        guestPlayers = 2;
                        showMessage(String.format("Waiting for players...(%d) guests", guestPlayers));
                        break;

                    case THREE_INNER:
                    case THREE_OUTER:
                    case TRIPLE_THREE:
                    case DOUBLE_THREE:
                        guestPlayers = 3;
                        showMessage(String.format("Waiting for players...(%d) guests", guestPlayers));

                    case FOUR_INNER:
                    case FOUR_OUTER:
                    case TRIPLE_FOUR:
                    case DOUBLE_FOUR:
                        guestPlayers = 4;
                        showMessage(String.format("Waiting for players...(%d) guests", guestPlayers));
                        break;

                    case FIVE_INNER:
                    case FIVE_OUTER:
                    case TRIPLE_FIVE:
                    case DOUBLE_FIVE:
                        guestPlayers = 5;
                        showMessage(String.format("Waiting for players...(%d) guests", guestPlayers));
                }
            }));
        });
    }

    @Override
    public void onPlayerAdded(Dartgame dartGame, Player player) {
        Platform.runLater(() -> {
            GamePlayer gamePlayer = new GamePlayer(player, dartGame.getPlayerScore(player), new AtomicBoolean(false));
            gamePlayerMap.put(player, gamePlayer);
            playersList.getItems().add(gamePlayer);

            TableColumn column = new TableColumn<GameRound, Player>(player.getName());
            column.setCellValueFactory(new MapValueFactory<>(player.getName()));
            column.setPrefWidth(150.0);

            scoreTable.getColumns().add(column);
            scoreTable.setPrefWidth(scoreTable.getPrefWidth() + column.getPrefWidth() + 1);

        });
    }

    @Override
    public void onGamePlayStarted(Dartgame dartGame) {
        Platform.runLater(() -> {
            showMessage("Get ready!");
            dartgame.attachDartboard(new PlatformDartboardWrapper(dartboardService.getDartboard()));
        });
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        Platform.runLater(() -> {
            currentScoreRound = new LinkedHashMap<>();
            currentScoreRound.put(SCORETABLEKEY_ROUND, String.format("#%d", roundNumber));
            currentScoreRound.putAll(dartgame.getPlayers().stream()
                    .collect(Collectors.toMap(
                            Player::getName,
                            player -> new AtomicInteger(0))));
            scoreData.add(currentScoreRound);
            scoreTable.getSelectionModel().select(currentScoreRound);
            scoreTable.scrollTo(currentScoreRound);

            currentRound = roundNumber;
            dartsList.getItems().clear();

            showMessage("Round #" + roundNumber);
            //wait(1);
        });
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        Platform.runLater(() -> {
            playersList.getSelectionModel().select(gamePlayerMap.get(currentPlayer = player));
            dartsList.getItems().clear();
            updateScore();

            showMessage(player.getName() + ": Throw the dart");
        });
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        Platform.runLater(() -> {
            hideMessage();

            dartsList.getItems().add(dart);
            updateScore();
        });
    }

    @Override
    public void onRemoveDarts(Dartgame dartGame, int round, Player player) {
        Platform.runLater(() -> {
            showMessage("Remove the darts");
        });
    }

    @Override
    public void onPlayerBust(Dartgame dartGame, Player player) {
        Platform.runLater(() -> {
            showMessage(player.getName() + " is bust");
        });
    }

    @Override
    public void onPlayerLost(Dartgame dartGame, Player player) {
        Platform.runLater(() -> {
            showMessage(player.getName() + " is out");
        });
    }

    @Override
    public void onPlayerWon(Dartgame dartGame, Player player) {
        Platform.runLater(() -> {
            showMessage(player.getName() + " is a winner");
        });
    }

    @Override
    public void onGameFinished(Dartgame dartGame) {
        Platform.runLater(() -> {
            showMessage("Game over");
            //show scores
            dartboardService.getDartboard().setOnDartboardValue(dartboardValue -> platformRun(() -> {
                LOG.debug("Dartboard value: {}", dartboardValue);
                switch (dartboardValue) {
                    case RED_BUTTON:
                        eventPublisherService.publish(new EndedDartgameEvent(this));
                        break;
                }
            }));
        });
    }

    private void updateScore() {
        PlayerScore currentPlayerScore = dartgame.getPlayerScore(currentPlayer);
        currentPlayerScoreLabel.setVisible(true);
        currentPlayerScoreLabel.setText(String.valueOf(currentPlayerScore.getScore()));

        ((AtomicInteger) currentScoreRound.get(currentPlayer.getName())).set(currentPlayerScore.getDartsForRound(currentRound).stream()
                .map(dartValueMapper::getDartScore)
                .reduce(0, Integer::sum));

        if (currentRound > 1) {
            Player leadPlayer = dartgame.getScoreBoard().getLeadingPlayer();

            gamePlayerMap.values().stream().forEach(gamePlayer ->
                    gamePlayer.leaderScore.set(gamePlayer.player.equals(leadPlayer)));
        }

        playersList.refresh();
        scoreTable.refresh();
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
        messagePane.setVisible(true);
    }

    private void hideMessage() {
        messagePane.setVisible(false);
    }

    private void platformRun(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            LOG.trace("Running on JavaFX thread");
            runnable.run();
        } else {
            LOG.trace("Running on non-JavaFX thread, so scheduling to run on it");
            Platform.runLater(runnable);
        }
    }

}
