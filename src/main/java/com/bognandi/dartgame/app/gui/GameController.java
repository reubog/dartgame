package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.domain.dartgame.*;
import com.bognandi.dartgame.domain.x01game.X01Dartgame;
import com.bognandi.dartgame.domain.x01game.X01ScoreBoard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class GameController extends DefaultDartgameEventListener {

    @Autowired
    private DartValueMapper dartValueMapper;

    @Autowired
    private GamePopups gamePopups;

    @FXML
    private BorderPane gamePane;

    @FXML
    private TableView scoreTable;

    @FXML
    private ListView<Dart> dartsList;

    @FXML
    private ListView<GamePlayer> playersList;

    @FXML
    private Label currentPlayerScoreLabel;

    @FXML
    private Label messageLabel;

    private X01Dartgame dartgame;
    private Player currentPlayer;
    private int currentRound;
    private Popup currentPopup;

    public record GamePlayer(Player player, PlayerScore playerScore, AtomicBoolean leaderScore) {
    }

    private Map<Player, GamePlayer> gamePlayerMap = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        playersList.setCellFactory(listView -> new GamePlayerListCell());
        dartsList.setCellFactory(listView -> new DartListCell());

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
            messageLabel.setText("Väntar på spelare");
            currentPopup = gamePopups.popupGameMessage(gamePane.getScene().getWindow(), "Waiting for players...");
        });
    }

    @Override
    public void onPlayerAdded(Dartgame dartGame, Player player) {
        Platform.runLater(() -> {
            GamePlayer gamePlayer = new GamePlayer(player, dartGame.getPlayerScore(player), new AtomicBoolean(false));
            gamePlayerMap.put(player, gamePlayer);
            playersList.getItems().add(gamePlayer);
        });
    }

    @Override
    public void onGameStarted(Dartgame dartGame) {
        Platform.runLater(() -> {
            messageLabel.setText("Spel startat");

            currentPopup.hide();
            currentPopup = gamePopups.popupGameMessage(gamePane.getScene().getWindow(), "Get ready!");
            wait(3);
        });
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        Platform.runLater(() -> {
            messageLabel.setText("Runda " + roundNumber + " startad");
            //roundsList.getItems().add("" + roundNumber);
            //roundsList.getSelectionModel().select("" + roundNumber);
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
            messageLabel.setText("Spelare " + player.getName() + "s tur");
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
        currentPlayerScoreLabel.setText(String.valueOf(dartgame.getPlayerScore(currentPlayer).getScore()));

        if (currentRound > 1) {
            Player leadPlayer = dartgame.getScoreBoard().getLeadingPlayer();

            gamePlayerMap.values().stream().forEach(gamePlayer ->
                    gamePlayer.leaderScore.set(gamePlayer.player.equals(leadPlayer)));
        }

        playersList.refresh();
    }
}
