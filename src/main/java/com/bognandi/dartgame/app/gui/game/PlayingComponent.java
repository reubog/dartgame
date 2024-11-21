package com.bognandi.dartgame.app.gui.game;

import javafx.scene.layout.BorderPane;

public class PlayingComponent extends BorderPane {

    private DartThrownPaneComponent dartThrownPaneComponent = new DartThrownPaneComponent();
    private PlayersPaneComponent playersPaneComponent = new PlayersPaneComponent();
    private RoundsPaneComponent roundsPaneComponent = new RoundsPaneComponent();
    private ScorePaneComponent scorePaneComponent = new ScorePaneComponent();

    public PlayingComponent() {

        setRight(dartThrownPaneComponent);
        setBottom(playersPaneComponent);
        setLeft(roundsPaneComponent);
        setCenter(scorePaneComponent);
    }

    public void initBindings(GameViewModel gameViewModel) {
        dartThrownPaneComponent.getDart1().bindDartValue(gameViewModel.dartValue1Property());
        dartThrownPaneComponent.getDart2().bindDartValue(gameViewModel.dartValue2Property());
        dartThrownPaneComponent.getDart3().bindDartValue(gameViewModel.dartValue3Property());

        playersPaneComponent.bindPlayers(gameViewModel.playerListProperty());

        roundsPaneComponent.bindRounds(gameViewModel.roundListProperty());

        scorePaneComponent.bindScore(gameViewModel.scoreProperty());
    }
}
