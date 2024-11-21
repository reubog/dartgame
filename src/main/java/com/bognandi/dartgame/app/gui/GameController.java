package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.app.gui.game.Notifications;
import com.bognandi.dartgame.app.gui.game.Round;
import com.bognandi.dartgame.app.service.GameAppService;
import com.bognandi.dartgame.app.service.GameService;
import com.bognandi.dartgame.domain.dartgame.Dart;
import com.bognandi.dartgame.domain.dartgame.DartValueMapper;
import com.bognandi.dartgame.domain.dartgame.DefaultDartgameEventListener;
import com.bognandi.dartgame.domain.dartgame.Player;
import com.bognandi.dartgame.domain.x01game.X01Dartgame;
import com.bognandi.dartgame.domain.x01game.X01ScoreBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameController extends DefaultDartgameEventListener {

    @Autowired
    private Notifications notifications;

    @Autowired
    private GameService gameService;

    @Autowired
    private DartValueMapper dartValueMapper;

    @FXML
    private BorderPane gamePane;

    @FXML
    private ListView<Round> roundsList;

    @FXML
    private ListView<Dart> dartsList;

    @FXML
    private ListView<Player> playersList;

    @FXML
    private Label currentPlayerScoreLabel;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        initNotifiations();
        playersList.setCellFactory(listView -> new PlayerListCell());

        gameService.startGame(new X01Dartgame(new X01ScoreBoard(301, dartValueMapper)));
        gameService.addPlayer("Player 1");
        gameService.addPlayer("Player 2");
        gameService.addPlayer("Player 3");






//        currentPlayerScoreLabel.setText("Noll");
//        messageLabel.setText("Välkommen");
//
//        roundsList.setgetItems().add("Runda 1");
//        roundsList.getItems().add("Runda 2");
//        roundsList.getItems().add("Runda 3");
//
//        dartsList.getItems().add("Dart 1");
//        dartsList.getItems().add("Dart 2");
//        dartsList.getItems().add("Dart 3");
//
//        playersList.getItems().add("Player 1");
//        playersList.getItems().add("Player 2");
//        playersList.getItems().add("Player 3");

    }

    private void initNotifiations() {
        notifications.subscribe(Notifications.WAITING_FOR_PLAYERS, this, t -> waitingForPlayers());
        notifications.subscribe(Notifications.PLAYER_ADDED, this, t -> updatePlayerList());
        notifications.subscribe(Notifications.START_GAME, this, t -> gameStarted());
        notifications.subscribe(Notifications.PLAYER_TURN, this, t -> playerTurn());
    }

    private void waitingForPlayers() {
        messageLabel.setText("Väntar på spelare");
    }

    private void updatePlayerList() {
        int index = playersList.getItems().size();
        for (int i=index; i<gameService.getPlayers().size(); i++) {
            Player player = gameService.getPlayers().get(i);
            playersList.getItems().add(player);
        }
    }

    private void gameStarted() {
        messageLabel.setText("Spel startat");
    }

    private void playerTurn() {
        messageLabel.setText("Spelare " + gameService.getCurrentPlayer().getName() + "s tur");
        currentPlayerScoreLabel.setText(String.valueOf(gameService.getCurrentPlayerScore()));
    }
}
