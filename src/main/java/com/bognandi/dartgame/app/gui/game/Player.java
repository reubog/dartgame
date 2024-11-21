package com.bognandi.dartgame.app.gui.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public record Player(
        StringProperty nameProperty,
        StringProperty scoreProperty,
        BooleanProperty currentPlayerProperty
) {
}
