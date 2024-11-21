package com.bognandi.dartgame.app.gui.game;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DartThrownComponent extends HBox {

    private Label dartvalue = new Label();
    private Label multiplier = new Label();

    public DartThrownComponent() {
        getChildren().add(dartvalue);
        getChildren().add(multiplier);
    }

    public void bindDartValue(DartValuePropertyWrapper wrapper) {
        dartvalue.textProperty().bind(wrapper.value());
        multiplier.textProperty().bind(wrapper.multiplier());
    }

}
