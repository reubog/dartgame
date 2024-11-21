package com.bognandi.dartgame.app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerListCellController {

    @FXML
    private Label playerNameLabel;

    public void updatePlayerName(String playerName) {
        playerNameLabel.setText(playerName);
    }
}
