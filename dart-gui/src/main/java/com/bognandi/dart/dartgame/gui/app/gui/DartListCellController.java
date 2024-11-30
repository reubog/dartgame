package com.bognandi.dart.dartgame.gui.app.gui;

import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.DartValueMapper;
import com.bognandi.dart.core.dartgame.DefaultDartValueMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
                case 2 -> dartLabel.setText(String.format("= %d", dartValueMapper.getDartValue(dart)));
                case 3 -> dartLabel.setText(String.format("≡ %d", dartValueMapper.getDartValue(dart)));
            }
        }
    }
}
