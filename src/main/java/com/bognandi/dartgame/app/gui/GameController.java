package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.domain.dartgame.*;
import com.bognandi.dartgame.domain.x01game.X01Dartgame;
import com.bognandi.dartgame.domain.x01game.X01ScoreBoard;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class GameController extends DefaultDartgameEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private static final String SCORETABLEKEY_ROUND = "round";

    @Autowired
    private DartValueMapper dartValueMapper;

    @Autowired
    private GamePopups gamePopups;

    @FXML
    private BorderPane gamePane;

    @FXML
    private TableView<Map<String,Object>> scoreTable;

    @FXML
    private ListView<Dart> dartsList;

    @FXML
    private ListView<GamePlayer> playersList;

    @FXML
    private Label currentPlayerScoreLabel;

    private X01Dartgame dartgame;
    private Player currentPlayer;
    private int currentRound;
    private Popup currentPopup;
    private ObservableList<Map<String,Object>> scoreData = FXCollections.observableArrayList();
    private Map<String,Object> currentScoreRound;

    public record GamePlayer(Player player, PlayerScore playerScore, AtomicBoolean leaderScore) {
    }

    public record GameRound(int round, Map<Player, AtomicInteger> score) {
    }

    private Map<Player, GamePlayer> gamePlayerMap = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        playersList.setCellFactory(listView -> new GamePlayerListCell());
        dartsList.setCellFactory(listView -> new DartListCell());

        TableColumn column = new TableColumn<GameRound, String>("Round");
        column.setCellValueFactory(new MapValueFactory<>(SCORETABLEKEY_ROUND));
        column.setPrefWidth(100.0);

        scoreTable.getColumns().add(column);
        scoreTable.setPrefWidth(column.getPrefWidth() + 1);
        scoreTable.setItems(scoreData);
        scoreTable.setPrefHeight(100.0);

        dartgame = new X01Dartgame(new X01ScoreBoard(301, dartValueMapper));
        dartgame.addEventListener(this);

        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        service.submit(() -> spela());
    }

    private void spela() {
        wait(1);
        dartgame.startGame();
        wait(1);
        dartgame.addPlayer(new DefaultPlayer("Player 1"));
        wait(1);
        dartgame.addPlayer(new DefaultPlayer("Player 2"));
        wait(1);
        dartgame.addPlayer(new DefaultPlayer("Player 3"));
        wait(1);

        dartgame.onButton();
        wait(1);

        for (int round = 0; round < 5; round++) {
            for (int player = 0; player < dartgame.getPlayers().size(); player++) {
                dartgame.onDartThrown(randomDart());
                wait(1);
                dartgame.onDartThrown(randomDart());
                wait(1);
                dartgame.onDartThrown(randomDart());
                wait(1);

                dartgame.onButton();
                wait(1);
            }
        }
    }

    private Dart randomDart() {
        return Dart.values()[new Random().nextInt(Dart.values().length)];
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameStarting(Dartgame dartGame) {
        Platform.runLater(() -> {
            currentPopup = gamePopups.popupGameMessage(gamePane.getScene().getWindow(), "Waiting for players...");
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
    public void onGameStarted(Dartgame dartGame) {
        Platform.runLater(() -> {
            currentPopup.hide();
            currentPopup = gamePopups.popupGameMessage(gamePane.getScene().getWindow(), "Get ready!");
            wait(3);
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

            currentRound = roundNumber;
            dartsList.getItems().clear();

            currentPopup.hide();
            currentPopup = gamePopups.popupGameMessage(gamePane.getScene().getWindow(), "Round #" + roundNumber);
            wait(1);
        });
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        Platform.runLater(() -> {
            playersList.getSelectionModel().select(gamePlayerMap.get(currentPlayer = player));
            dartsList.getItems().clear();
            updateScore();

            currentPopup.hide();
            currentPopup = gamePopups.popupGameMessage(gamePane.getScene().getWindow(), player.getName() + ": Throw the dart");
        });
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        Platform.runLater(() -> {
            currentPopup.hide();

            dartsList.getItems().add(dart);
            updateScore();
        });
    }

    private void updateScore() {
        PlayerScore currentPlayerScore = dartgame.getPlayerScore(currentPlayer);
        currentPlayerScoreLabel.setText(String.valueOf(currentPlayerScore.getScore()));

        ((AtomicInteger)currentScoreRound.get(currentPlayer.getName())).set(currentPlayerScore.getDartsForRound(currentRound).stream()
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

}
