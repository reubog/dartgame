package com.bognandi.dartgame.app.gui.game;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlayerComponent extends HBox {

    private Label name = new Label();
    private Label score = new Label();

    public PlayerComponent() {
        getChildren().add(name);
        getChildren().add(score);
    }

    public void bindPlayer(Player playerProperty) {
        name.textProperty().bind(playerProperty.nameProperty());
        score.textProperty().bind(playerProperty.scoreProperty());
    }
}
