package com.bognandi.dartgame.app.gui.game;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RoundComponent extends HBox {

    private Label round = new Label();
    private Label score = new Label();

    public RoundComponent() {
        getChildren().add(round);
        getChildren().add(score);
    }

    public void bindRound(Round roundProperty) {
        round.textProperty().bind(roundProperty.round());
        score.textProperty().bind(roundProperty.score());
    }
}
