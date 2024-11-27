package com.bognandi.dart.dartgame.gui.app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GamePlayerListCellController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private ImageView leaderImage;

    public void updatePlayer(GameController.GamePlayer item) {
        playerNameLabel.setText(item.player().getName());
        scoreLabel.setText("" + item.playerScore().getScore());
        leaderImage.setVisible(item.leaderScore().get());
    }
}
