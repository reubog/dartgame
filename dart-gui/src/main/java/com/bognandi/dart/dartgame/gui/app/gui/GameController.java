package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.*;
import com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport;
import com.bognandi.dart.dartgame.gui.app.event.CloseDartgameEvent;
import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import com.bognandi.dart.dartgame.gui.app.event.StartDartgameEvent;
import com.bognandi.dart.dartgame.gui.app.service.DartgamesService;
import com.bognandi.dart.dartgame.gui.app.service.EventPublisherService;
import com.bognandi.dart.dartgame.gui.app.service.dartboard.DartboardService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameController extends DefaultDartgameEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private static final String BACKGROUND_VIDEO_RESOURCE = "/video/tavern.mp4";
    private static final String SCORETABLEKEY_ROUND = "round";

    private enum GameState {
        WAITING_FOR_PLAYERS,
        FINISHED,
        READY_TO_CLOSE,
    }

    @Autowired
    private DartValueMapper dartValueMapper;

    @Autowired
    private DartboardService dartboardService;

    @Autowired
    private DartgamesService dartgamesService;

    @Autowired
    private EventPublisherService eventPublisherService;

    @FXML
    private MediaView backgroundMediaView;

    @FXML
    private AnchorPane messagePane;

    @FXML
    private Label messageLabel;

    @FXML
    private Label guideTextLabel;

    @FXML
    private Label confirmButtonLabel;

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

    private GameState gameState;
    private Dartgame dartgame;
    private Player currentPlayer;
    private int currentRound;
    private ObservableList<Map<String, Object>> scoreData = FXCollections.observableArrayList();
    private Map<String, Object> currentScoreRound;
    private MediaPlayer mediaPlayer;
    private List<Player> players = new ArrayList<>();
    private int guestPlayers;
    private DartgameDescriptor dartgameDescriptor;

    public class GamePlayer {
        String playerName;
        int playerScore;
        boolean leader;

        public GamePlayer(String playerName, int playerScore, boolean leader) {
            this.playerName = playerName;
            this.playerScore = playerScore;
            this.leader = leader;
        }
    }

    public record GameRound(
            int round,
            Map<Player, AtomicInteger> score
    ) {
    }

    private Map<Player, GamePlayer> gamePlayerMap = new LinkedHashMap<>();
    private Stage stage;

    public GameController() {
        LOG.debug("GameController created");
    }

    @FXML
    public void initialize() {
        LOG.debug("Initializing game controller view");

        gamePlayerMap.clear();
        scoreData.clear();
        players.clear();
        playersList.getItems().clear();
        scoreTable.getItems().clear();
        scoreTable.getColumns().clear();
        dartsList.getItems().clear();
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
    public void saveStageForLater(StageReadyEvent event) {
        LOG.debug("Saving stage");
        stage = event.getStage();
    }

    @EventListener(StartDartgameEvent.class)
    public void startGameGui(StartDartgameEvent event) {
        LOG.debug("Switching view to Game mode");
        doEnsureFxThread(() -> {
            stage.getScene().setRoot(JavaFxApplicationSupport.GAME_PARENT);

            dartgameDescriptor = event.getDartgameDescriptor();

            waitingForPlayers();
        });
    }

    @FXML
    public void messageConfirmed() {
        if (gameState == null) {
            LOG.warn("Message confirmed, but no game state");
            return;
        }

        LOG.debug("Message confirmed, gamestate={}", gameState);
        switch (gameState) {
            case WAITING_FOR_PLAYERS:
                addPlayersAndStartGame();
                break;

            case FINISHED:
                showMessageConfirm("Game is finished");
                gameState = GameState.READY_TO_CLOSE;
                break;

            case READY_TO_CLOSE:
                eventPublisherService.publish(new CloseDartgameEvent(GameController.this));
                break;

            default:
                LOG.warn("Unknown game state: {}", gameState);
                gameState = null;
        }
    }

    private void addPlayersAndStartGame() {
        if (guestPlayers  < dartgameDescriptor.getMinimumPlayerCount()) {
            LOG.debug("Too few players");
            return;
        }

        if (guestPlayers > dartgameDescriptor.getMaximumPlayerCount()) {
            LOG.debug("Too many players");
            return;
        }

        addPlayersToGame();

        LOG.debug("Starting game");
        dartgame = dartgamesService.createDartgame(dartgameDescriptor);
        dartgame.addEventListener(this);
        dartgame.attachDartboard(new EnsureJavaFXThreadDartboardWrapper(dartboardService.getDartboard()));
        dartgame.setPlayers(players);
        dartgame.startPlaying();
    }

    private void waitingForPlayers() {
        showMessageGuideText("Waiting for players...", "Use dartboard numbers to select");
        gameState = GameState.WAITING_FOR_PLAYERS;
        dartboardService.getDartboard().setOnDartboardValue(dartboardValue -> doEnsureFxThread(() -> {
            LOG.debug("Dartboard value: {}", dartboardValue);
            switch (dartboardValue) {
                case RED_BUTTON:
                    addPlayersAndStartGame();
                    break;

                case TWO_INNER:
                case TWO_OUTER:
                case TRIPLE_TWO:
                case DOUBLE_TWO:
                    guestPlayers = 2;
                    showMessageConfirm(String.format("Waiting for players...(%d) guests", guestPlayers));
                    break;

                case THREE_INNER:
                case THREE_OUTER:
                case TRIPLE_THREE:
                case DOUBLE_THREE:
                    guestPlayers = 3;
                    showMessageConfirm(String.format("Waiting for players...(%d) guests", guestPlayers));
                    break;

                case FOUR_INNER:
                case FOUR_OUTER:
                case TRIPLE_FOUR:
                case DOUBLE_FOUR:
                    guestPlayers = 4;
                    showMessageConfirm(String.format("Waiting for players...(%d) guests", guestPlayers));
                    break;

                case FIVE_INNER:
                case FIVE_OUTER:
                case TRIPLE_FIVE:
                case DOUBLE_FIVE:
                    guestPlayers = 5;
                    showMessageConfirm(String.format("Waiting for players...(%d) guests", guestPlayers));
            }
        }));
    }

    private void addPlayersToGame() {
        LOG.debug("Add players");

        players.addAll(IntStream.range(0, guestPlayers)
                .mapToObj(i -> (Player) new DefaultPlayer("Guest " + (i + 1)))
                .toList());

        players.forEach(player -> {
            LOG.debug("Adding player: {}", player.getName());

            GamePlayer gamePlayer = new GamePlayer(
                    player.getName(),
                    0,
                    false);

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
    public void onPlayersSet(Dartgame dartGame, List<Player> players) {
        doEnsureFxThread(() -> {


        });
    }

    @Override
    public void onGamePlayStarted(Dartgame dartGame) {
        doEnsureFxThread(() -> {
            showMessageForDuration(Duration.ofSeconds(3), "Get ready!");
        });
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        doEnsureFxThread(() -> {
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

            showMessageForDuration(Duration.ofSeconds(3), "Round #" + roundNumber);
        });
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        doEnsureFxThread(() -> {
            playersList.getSelectionModel().select(gamePlayerMap.get(currentPlayer = player));
            dartsList.getItems().clear();
            updateScore();

            showMessageForDuration(Duration.ofSeconds(3), player.getName() + ": Throw the dart");
        });
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        doEnsureFxThread(() -> {
            hideMessage();

            dartsList.getItems().add(dart);
            updateScore();
        });
    }

    @Override
    public void onRemoveDarts(Dartgame dartGame, int round, Player player) {
        doEnsureFxThread(() -> {
            showMessageConfirm("Remove the darts");
            updateScore();
        });
    }

    @Override
    public void onPlayerBust(Dartgame dartGame, Player player) {
        doEnsureFxThread(() -> {
            showMessageConfirm(player.getName() + " is bust\nRemove the darts");
        });
    }

    @Override
    public void onPlayerLost(Dartgame dartGame, Player player) {
        doEnsureFxThread(() -> {
            showMessageConfirm(player.getName() + " is out\nRemove the darts");
        });
    }

    @Override
    public void onPlayerWon(Dartgame dartGame, Player player) {
        doEnsureFxThread(() -> {
            if (dartGame.isPlaying()) {
                showMessageConfirm(player.getName() + " is a winner\nRemove the darts");
            } else {
                showMessageConfirm(player.getName() + " is a winner");
                gameState = GameState.FINISHED;
                // TODO show scores
                dartboardService.getDartboard().setOnDartboardValue(dartboardValue -> doEnsureFxThread(() -> {
                    LOG.debug("Dartboard value: {}", dartboardValue);
                    switch (dartboardValue) {
                        case RED_BUTTON:
                            messageConfirmed();
                            break;
                    }
                }));
            }

        });
    }

    @Override
    public void onGameFinished(Dartgame dartGame) {
        LOG.debug("Game finished");
    }

    private void updateScore() {
        LOG.debug("updating score");
        PlayerScore currentPlayerScore = dartgame.getPlayerScore(currentPlayer);
        currentPlayerScoreLabel.setVisible(true);
        currentPlayerScoreLabel.setText(String.valueOf(currentPlayerScore.getScore()));

        int scoreTotalDartsForCurrentRound = currentPlayerScore.getDartsForRound(currentRound).stream()
                .map(dartValueMapper::getDartScore)
                .reduce(0, Integer::sum);

        ((AtomicInteger) currentScoreRound.get(currentPlayer.getName())).set(scoreTotalDartsForCurrentRound);

        boolean showLeadPlayer = currentRound > 1;
        Player leadPlayer = dartgame.getScoreBoard().getLeadingPlayer();

        gamePlayerMap.entrySet().stream().forEach(entry -> {
            Player player = entry.getKey();
            GamePlayer gamePlayer = entry.getValue();
            gamePlayer.playerScore = dartgame.getPlayerScore(player).getScore();
            gamePlayer.leader = showLeadPlayer && player.equals(leadPlayer);
        });

        playersList.refresh();
        scoreTable.refresh();
    }

    private void showMessageForDuration(Duration duration, String message) {
        messageLabel.setText(message);
        messagePane.setVisible(true);
        guideTextLabel.setVisible(false);
        confirmButtonLabel.setVisible(false);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        doEnsureFxThread(() -> {
                            LOG.debug("Hiding message: {}", message);
                            if (messageLabel.getText().equals(message)) {
                                messagePane.setVisible(false);
                            } else {
                                LOG.debug("Message changed, so not hiding message");
                            }
                        });
                    }
                },
                duration.toMillis()
        );
    }

    private void showMessageConfirm(String message) {
        messageLabel.setText(message);
        guideTextLabel.setVisible(false);
        confirmButtonLabel.setVisible(true);
        messagePane.setVisible(true);
    }

    private void showMessageGuideText(String message, String guideText) {
        messageLabel.setText(message);
        guideTextLabel.setText(guideText);
        guideTextLabel.setVisible(true);
        confirmButtonLabel.setVisible(false);
        messagePane.setVisible(true);
    }

    private void hideMessage() {
        messagePane.setVisible(false);
    }

    private void doEnsureFxThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            //LOG.trace("Running on JavaFX thread");
            runnable.run();
        } else {
            //LOG.trace("Running on non-JavaFX thread, so scheduling to run on it");
            Platform.runLater(runnable);
        }
    }

}
