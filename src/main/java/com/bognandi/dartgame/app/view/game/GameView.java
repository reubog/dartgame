package com.bognandi.dartgame.app.view.game;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView extends VBox {

    private final GameViewModel viewModel;

    private HBox gameBox = new HBox();
    private HBox playersBox = new HBox();

    private VBox dartsBox = new VBox();
    private VBox roundsBox = new VBox();
    private VBox scoresBox = new VBox();

    private Label scoreLabel = new Label();

    private Label dartLabel1 = new Label();
    private Label dartLabel2 = new Label();
    private Label dartLabel3 = new Label();

    public GameView(GameViewModel viewModel) {
        this.viewModel = viewModel;
        initLayout();
        initListeners();
    }

    private void initLayout() {
        getChildren().add(gameBox);
        getChildren().add(playersBox);

        gameBox.getChildren().add(roundsBox);
        gameBox.getChildren().add(scoresBox);
        gameBox.getChildren().add(dartsBox);

        scoresBox.getChildren().add(scoreLabel);

        dartsBox.getChildren().add(dartLabel1);
        dartsBox.getChildren().add(dartLabel2);
        dartsBox.getChildren().add(dartLabel3);
    }

    private void initListeners() {
        viewModel.dartValue1Property().addListener((observable, oldValue, newValue) -> dartLabel1.setText(newValue));
        viewModel.dartValue2Property().addListener((observable, oldValue, newValue) -> dartLabel2.setText(newValue));
        viewModel.dartValue3Property().addListener((observable, oldValue, newValue) -> dartLabel3.setText(newValue));

        viewModel.scoreProperty().addListener((observable, oldValue, newValue) -> scoreLabel.setText(newValue));
    }
}
