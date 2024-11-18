package com.bognandi.dartgame.app.gui.game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameViewModel {

    private final Notifications notifications = Notifications.createInstance();
    private final GameModel model = new GameModel();

    private StringProperty dartValue1 = new SimpleStringProperty("dart 1");
    private StringProperty dartValue2 = new SimpleStringProperty("dart 2");
    private StringProperty dartValue3 = new SimpleStringProperty("dart 3");

    private StringProperty score = new SimpleStringProperty("S C O R E");

    public GameViewModel() {
        initSubscriptions();
    }

    private void initSubscriptions() {
        notifications.subscribe(GameModel.GAME_STARTING, this, event -> {
            dartValue1.setValue("-");
            dartValue2.setValue("-");
            dartValue3.setValue("-");

            score.setValue("-");
        });

        notifications.subscribe(GameModel.GAME_STARTED, this, event -> {
            dartValue1.setValue("-");
            dartValue2.setValue("-");
            dartValue3.setValue("-");

            score.setValue("-");
        });
    }

    public void doGameSelection() {

    }

    public void doSelectGame(String id) {
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
