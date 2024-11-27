package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.Player;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class RoundScoreTableColumn extends TableColumn<GameController.GameRound, Player> {
    public RoundScoreTableColumn() {
        super();
        setCellFactory(roundColumn -> new TableCell<>(){
            @Override
            protected void updateItem(Player player, boolean b) {
                super.updateItem(player, b);
                if (b || player == null) {
                    setText(null);
                } else {
                    setText(player.getName());
                }
            }

            {

        }});
    }

}
