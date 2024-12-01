package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.DartgameDescriptor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameDescriptorListCellController {

    @FXML
    private Label titleLabel;

    public void updateItem(DartgameDescriptor item) {
        titleLabel.setText(item.getTitle());
    }
}
