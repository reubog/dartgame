package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.DartgameDescriptor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GameDescriptorListCell extends ListCell<DartgameDescriptor> {

    private static final Logger LOG = LoggerFactory.getLogger(GameDescriptorListCell.class);

    private Parent root;
    private GameDescriptorListCellController controller;

    public GameDescriptorListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameDescriptorListCell.fxml"));
            root = loader.load();
            controller = loader.getController();
            LOG.trace("loaded, controller={}, root={}", controller, root);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(DartgameDescriptor item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            LOG.trace("updateItem: null");
            setGraphic(null);
        } else {
            LOG.trace("updateItem: {}", item);
            controller.updateItem(item);
            setGraphic(root);
        }
    }
}
