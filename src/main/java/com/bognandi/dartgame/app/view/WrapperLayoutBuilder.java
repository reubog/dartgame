package com.bognandi.dartgame.app.view;

import com.bognandi.dartgame.app.view.game.GameView;
import com.bognandi.dartgame.app.view.gameselection.GameSelectionView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Builder;

import javax.swing.plaf.synth.Region;

public class WrapperLayoutBuilder implements Builder<Region> {

    private Builder<Region> gameSelectionComponent;
    private Builder<Region> gameView;

    @Override
    public Region build() {
        BorderPane results = new BorderPane();

        results.setTop(new Label("This is The Wrapper"));

        String

        gameSelectionView = new GameSelectionView((gameId) -> results.setCenter(gameView)).build();
        gameView = new GameView(() -> results.setCenter(gameSelectionComponent)).build();

        // start with this
        results.setCenter(customComponent1);

        return results;
    }
}
