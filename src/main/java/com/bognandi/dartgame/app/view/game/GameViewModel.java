package com.bognandi.dartgame.app.view.game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameViewModel {

    private GameModel model;

    private StringProperty dartValue1 = new SimpleStringProperty("dart 1");
    private StringProperty dartValue2 = new SimpleStringProperty("dart 2");
    private StringProperty dartValue3 = new SimpleStringProperty("dart 3");

    private StringProperty score = new SimpleStringProperty("S C O R E");

    public GameViewModel(GameModel model) {
        this.model = model;
    }

    public StringProperty dartValue1Property() {
        return dartValue1;
    }

    public StringProperty dartValue2Property() {
        return dartValue2;
    }

    public StringProperty dartValue3Property() {
        return dartValue3;
    }

    public StringProperty scoreProperty() {
        return score;
    }
}
