package com.bognandi.dartgame.app.gui.game;

import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class GameView extends StackPane {

    @Autowired
    private GameViewModel gameViewModel;

    private Runnable nextScene;

    private PlayingComponent playingComponent = new PlayingComponent();
    private WaitingForPlayersComponent waitingForPlayersPaneComponent = new WaitingForPlayersComponent();

    public GameView() {
        initLayout();
        initBindings();
    }

    private void initLayout() {
        getChildren().add(playingComponent);
        getChildren().add(waitingForPlayersPaneComponent);
    }

    private void initBindings() {
        playingComponent.initBindings(gameViewModel);
        waitingForPlayersPaneComponent.visibleProperty().bind(gameViewModel.waitingForPlayersProperty());
    }

    public void setNextScene(Runnable nextScene) {
        this.nextScene = nextScene;
    }

}
