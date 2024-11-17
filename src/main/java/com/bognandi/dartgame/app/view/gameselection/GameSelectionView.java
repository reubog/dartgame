package com.bognandi.dartgame.app.view.gameselection;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

import java.util.function.Consumer;

public class GameSelectionView extends VBox implements Builder<Region> {

    private final Consumer<String> gameSelectionSupplier;
    private final GameSelectionViewModel viewModel = new GameSelectionViewModel();

    private Label titleLabel = new Label("Select Dart Game:");
    private ListView<GameInfo> games = new ListView();
    private Button startButton = new Button("Start Game");

    public GameSelectionView(Consumer<String> gameSelectionSupplier) {
        this.gameSelectionSupplier = gameSelectionSupplier;
        createView();
        createBindings();
    }

    @Override
    public Region build() {
        return this;
    }

    private void createView() {
        this.getChildren().add(titleLabel);
        this.getChildren().add(games);
        this.getChildren().add(startButton);

        this.setAlignment(javafx.geometry.Pos.CENTER);

        games.setCellFactory(listView -> new Cell());
    }

    private void createBindings() {
        games.itemsProperty().bind(viewModel.gameInfosPropertyProperty());
        startButton.setOnAction(event -> gameSelectionSupplier.accept(games.getSelectionModel().getSelectedItem().id()));
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

}
