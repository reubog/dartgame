package com.bognandi.dartgame.app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;

@Component
public class GameController {

    @FXML
    private BorderPane gamePane;

    @FXML
    private ListView roundsList;

    @FXML
    private ListView dartsList;

    @FXML
    private ListView playersList;

    @FXML
    private Label currentPlayerScoreLabel;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        currentPlayerScoreLabel.setText("Noll");
        messageLabel.setText("Välkommen");

        roundsList.getItems().add("Runda 1");
        roundsList.getItems().add("Runda 2");
        roundsList.getItems().add("Runda 3");

        dartsList.getItems().add("Dart 1");
        dartsList.getItems().add("Dart 2");
        dartsList.getItems().add("Dart 3");

        playersList.getItems().add("Player 1");
        playersList.getItems().add("Player 2");
        playersList.getItems().add("Player 3");

    }
}
