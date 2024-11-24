package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.domain.dartgame.Dart;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DartListCell extends ListCell<Dart> {

    private static final Logger LOG = LoggerFactory.getLogger(DartListCell.class);

    private Parent root;
    private DartListCellController controller;

    public DartListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameDartListCell.fxml"));
            root = loader.load();
            controller = loader.getController();
            LOG.info("loaded, controller={}, root={}", controller, root);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(Dart item, boolean empty) {
        LOG.info("updateItem: {}", item);
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            controller.updateDart(item);
            setGraphic(root);
        }
    }
}
