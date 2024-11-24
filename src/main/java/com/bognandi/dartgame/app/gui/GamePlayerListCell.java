package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.domain.dartgame.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GamePlayerListCell extends ListCell<GameController.GamePlayer> {

    private static final Logger LOG = LoggerFactory.getLogger(GamePlayerListCell.class);

    private Parent root;
    private GamePlayerListCellController controller;

    public GamePlayerListCell() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gamePlayerListCell.fxml"));
                root = loader.load();
                controller = loader.getController();
                LOG.info("loaded, controller={}, root={}", controller, root);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
    }

    @Override
    protected void updateItem(GameController.GamePlayer item, boolean empty) {
        LOG.info("updateItem: {}", item);
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            controller.updatePlayer(item);
            setGraphic(root);
        }
    }
}
