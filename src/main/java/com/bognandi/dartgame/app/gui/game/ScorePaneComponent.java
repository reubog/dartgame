package com.bognandi.dartgame.app.gui.game;

import javafx.beans.property.Property;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class ScorePaneComponent extends BorderPane {

    private Label scoreLabel = new Label();

    public ScorePaneComponent() {
        VBox vBox = new VBox();
        vBox.getChildren().add(scoreLabel);

        setCenter(vBox);
    }

    public void bindScore(Property<String> property) {
        scoreLabel.textProperty().bind(property);
    }
}
