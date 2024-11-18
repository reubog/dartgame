package com.bognandi.dartgame.app.view;

import com.bognandi.dartgame.app.view.game.GameView;
import com.bognandi.dartgame.app.view.gameselection.GameSelectionView;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Builder;

public class WrapperLayoutBuilder implements Builder<Parent> {

    private Builder<Parent> gameSelectionComponent;
    private Builder<Parent> gameComponent;

    @Override
    public Parent build() {
        BorderPane results = new BorderPane();

        results.setTop(new Label("This is The Wrapper"));

        gameSelectionComponent = new GameSelectionView(() -> results.setCenter(gameComponent.build()));
        gameComponent = new GameView(() -> results.setCenter(gameSelectionComponent.build()));

        // start with this
        results.setCenter(gameSelectionComponent.build());

        return results;
    }
}
