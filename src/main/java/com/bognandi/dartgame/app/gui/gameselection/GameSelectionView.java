package com.bognandi.dartgame.app.gui.gameselection;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class GameSelectionView extends VBox {

    private GameSelectionViewModel gameSelectionViewModel = new GameSelectionViewModel();

    private Runnable nextScene;

    private Label titleLabel = new Label("Select Dart Game:");
    private ListView<GameInfo> games = new ListView<>();
    private Button startButton = new Button("Start Game");

    public GameSelectionView() {
        createView();
        createBindings();
    }

    private void createView() {
        this.getChildren().add(titleLabel);
        this.getChildren().add(games);
        this.getChildren().add(startButton);

        this.setAlignment(javafx.geometry.Pos.CENTER);

        games.setCellFactory(listView -> new Cell());
    }

    private void createBindings() {
        games.itemsProperty().bind(gameSelectionViewModel.gameInfosPropertyProperty());
        startButton.setOnAction(e -> {
            gameSelectionViewModel.onSelectGame(games.getSelectionModel().getSelectedItem());
            nextScene.run();
        });
    }

    private class Cell extends ListCell<GameInfo> {
        @Override
        protected void updateItem(GameInfo item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.name());
                setAlignment(javafx.geometry.Pos.CENTER);
            }
        }
    }

    public void setNextScene(Runnable nextScene) {
        this.nextScene = nextScene;
    }

}
