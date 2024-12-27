package com.bognandi.dart.dartgame.gui.app.gui.score;

import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardValueMapper;
import com.bognandi.dart.core.dartgame.DartAvergeLevel;
import com.bognandi.dart.core.dartgame.DartValueMapper;
import com.bognandi.dart.core.dartgame.Player;
import com.bognandi.dart.core.dartgame.ScoreBoard;
import com.bognandi.dart.dartgame.gui.app.event.CloseScoreboardEvent;
import com.bognandi.dart.dartgame.gui.app.event.OpenScoreboardEvent;
import com.bognandi.dart.dartgame.gui.app.event.StageReadyEvent;
import com.bognandi.dart.dartgame.gui.app.service.EventPublisherService;
import com.bognandi.dart.dartgame.gui.app.service.dartboard.DartboardService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.bognandi.dart.dartgame.gui.app.JavaFxApplicationSupport.GAME_SCORE_PARENT;

@Component
public class ScoreController {

    private final static Logger LOG = LoggerFactory.getLogger(ScoreController.class);
    private static final String BACKGROUND_VIDEO_RESOURCE = "/video/mountain.mp4";
    private static final String SCORETABLEKEY_INFO = "info";

    private Stage stage;
    private List<Player> players;
    private ScoreBoard scoreBoard;
    private ObservableList<Map<String, String>> scoreData = FXCollections.observableArrayList();
    private MediaPlayer mediaPlayer;

    @Autowired
    private DartboardService dartboardService;

    @Autowired
    private EventPublisherService eventPublisherService;

    @Autowired
    private DartValueMapper dartValueMapper;

    @FXML
    private TableView<Map<String, String>> scoreTable;

    @FXML
    private MediaView mediaView;

    @FXML
    public void initialize() {
        scoreTable.setItems(scoreData);

        Media media = new Media(getClass().getResource(BACKGROUND_VIDEO_RESOURCE).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    @EventListener(StageReadyEvent.class)
    public void saveStageHandler(StageReadyEvent event) {
        stage = event.getStage();
    }

    @EventListener(OpenScoreboardEvent.class)
    public void openScoreboardEventHandler(OpenScoreboardEvent event) {
        platformRun(() -> {
            this.scoreBoard = event.getScoreboard();
            this.players = event.getPlayers();
            startGui();
        });
    }

    private void startGui() {
        LOG.debug("Opening scoreboard gui");

        setupTableColumns();
        setupTableData();

        mediaPlayer.setAutoPlay(true);

        setupDartboardHandler();

        stage.getScene().setRoot(GAME_SCORE_PARENT);
    }

    private void setupDartboardHandler() {
        LOG.debug("Setup dartboard handler");

        dartboardService.getDartboard().setOnDartboardValue(dartboardValue ->
                Platform.runLater(() -> setupDartboardHandler(dartboardValue)));
    }

    private void setupTableColumns() {
        LOG.debug("Setting up table columns");

        scoreTable.getColumns().clear();

        TableColumn headerColumn = new TableColumn<>("");
        headerColumn.setCellValueFactory(new MapValueFactory<>(SCORETABLEKEY_INFO));
        headerColumn.setPrefWidth(250.0);
        scoreTable.getColumns().add(headerColumn);

        for (Player player : players) {
            TableColumn column = new TableColumn<>(player.getName());
            column.setCellValueFactory(new MapValueFactory<>(player.getName()));
            column.setPrefWidth(150.0);
            scoreTable.getColumns().add(column);
        }
    }

    private void setupTableData() {
        LOG.debug("Setting up table data");

        scoreData.clear();

        scoreData.add(createScoreRowData(
                "Remaining Score",
                player -> String.valueOf(scoreBoard.getPlayerScore(player).getScore())
        ));

        scoreData.add(createScoreRowData(
                "Darts Thrown",
                player -> String.valueOf(scoreBoard.getPlayerScore(player).getThrownDarts().size())
        ));

        scoreData.add(createScoreRowData(
                "Played Rounds",
                player -> String.valueOf(scoreBoard.getPlayerScore(player).getPlayedRounds())
        ));

        scoreData.add(createScoreRowData(
                "Dart Average",
                player -> String.format("%#.1f", scoreBoard.getPlayerScore(player).getDartAverage())
        ));

        scoreData.add(createScoreRowData(
                "Points Per Dart",
                player -> String.format("%#.1f", scoreBoard.getPlayerScore(player).getPointsPerDart())
        ));

        scoreData.add(createScoreRowData(
                "Trebles",
                player -> String.format("%d", scoreBoard.getPlayerScore(player).getThrownDarts().stream()
                        .filter(dart -> dartValueMapper.multiplier(dart) == 3)
                        .count())
        ));

        scoreData.add(createScoreRowData(
                "Doubles",
                player -> String.format("%d", scoreBoard.getPlayerScore(player).getThrownDarts().stream()
                        .filter(dart -> dartValueMapper.multiplier(dart) == 2)
                        .count())
        ));

        scoreData.add(createScoreRowData(
                "Level",
                player -> findDartAverageLevelStr(scoreBoard.getPlayerScore(player).getDartAverage())
        ));

    }

    private Map<String, String> createScoreRowData(String text, Function<Player, String> playerValueSupplier) {
        Map<String, String> map = new LinkedHashMap<>();

        map.put(SCORETABLEKEY_INFO, text);
        players.forEach(player -> map.put(player.getName(), playerValueSupplier.apply(player)));

        return map;
    }

    private void setupDartboardHandler(DartboardValue value) {
        switch (value) {
            case RED_BUTTON -> eventPublisherService.publish(new CloseScoreboardEvent(scoreBoard));
        }
    }

    private String findDartAverageLevelStr(double  dartAverage) {
        return switch (DartAvergeLevel.findLevel((int) dartAverage)) {
            case BEGINNER -> "Beginner";
            case FIRST_TIMER -> "First timer";
            case PUB_PLAYER -> "Pub Player";
            case WORLD_CLASS -> "World Class";
            case PDC_TOUR_PLAYER -> "PDC Tour Player";
            case PDC_CHALLENGE_TOUR -> "PDC Challenge Tour";
            default -> "Unknown";
        };
    }

    private void platformRun(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
