package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.domain.dartgame.Dart;
import com.bognandi.dartgame.domain.dartgame.DartValueMapper;
import com.bognandi.dartgame.domain.dartgame.DefaultDartValueMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class DartListCellController {

    private DartValueMapper dartValueMapper = new DefaultDartValueMapper();

    @FXML
    private Label dartLabel;

    public void updateDart(Dart dart) {
        if (Dart.BULLSEYE.equals(dart)) {
            dartLabel.setText("Bullseye");
        } else if (Dart.DOUBLE_BULLSEYE.equals(dart)) {
            dartLabel.setText("2x Bullseye");
        } else {
            switch (dartValueMapper.multiplier(dart)) {
                case 1 -> dartLabel.setText(String.format("%d", dartValueMapper.getDartValue(dart)));
                case 2 -> dartLabel.setText(String.format("2x %d", dartValueMapper.getDartValue(dart)));
                case 3 -> dartLabel.setText(String.format("3x %d", dartValueMapper.getDartValue(dart)));
            }
        }
    }
}
