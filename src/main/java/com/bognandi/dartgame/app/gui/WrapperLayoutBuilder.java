package com.bognandi.dartgame.app.gui;

import com.bognandi.dartgame.app.gui.game.GameView;
import com.bognandi.dartgame.app.gui.gameselection.GameSelectionView;
import com.bognandi.dartgame.app.gui.settings.SettingsView;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class WrapperLayoutBuilder implements Builder<Parent> {

//    private SettingsView settingsView;

    private GameSelectionView gameSelectionComponent = new GameSelectionView();

   // private GameView gameView = new GameView();


    private BorderPane pane;

    public WrapperLayoutBuilder() {
        createView();
        createSceneTransitions();
    }

    private void createView() {
        pane = new BorderPane();
        pane.setTop(new HBox(){{
            getChildren().add(new Label("Top"));
            //getChildren().add(settingsView);
        }});
        pane.setCenter(gameSelectionComponent);
    }

    private void createSceneTransitions() {
       // gameSelectionComponent.setNextScene(() -> pane.setCenter(gameView));
       // gameView.setNextScene(() -> pane.setCenter(gameSelectionComponent));
    }

    @Override
    public Parent build() {
        return pane;
    }
}
