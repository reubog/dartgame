package com.bognandi.dart.dartgame.gui.app.gui;

import javafx.scene.control.TableCell;

public class GameRoundListCell extends TableCell<GameController.GameRound,String> {

    @Override
    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);
        if (empty || getItem() == null) {
            setText(null);
        } else {
            setText("fake");
        }
    }
}
