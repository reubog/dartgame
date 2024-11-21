package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.domain.dartgame.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PlayerListCell extends ListCell<Player> {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerListCell.class);

    private Parent root;
    private PlayerListCellController controller;

    public PlayerListCell() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayerListCell.fxml"));
                root = loader.load();
                controller = loader.getController();
                LOG.info("PlayerListCell loaded, controller={}, root={}", controller, root);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
    }

    @Override
    protected void updateItem(Player player, boolean empty) {
        LOG.info("PlayerListCell updateItem: {}", player);
        super.updateItem(player, empty);
        if (empty || player == null) {
            setGraphic(null);
        } else {
            controller.updatePlayerName(player.getName());
            setGraphic(root);
        }
    }
}
