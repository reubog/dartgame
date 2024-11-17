package com.bognandi.dartgame.app.view.gameselection;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class GameSelectionViewModel {

    private final GameSelectionModel model = new GameSelectionModel();
    private final GameInfoConverter converter = new GameInfoConverter();

    private final List<GameInfo> gameInfos = new ArrayList<>();

    private final ObservableList<GameInfo> gameInfoObservableList = FXCollections.observableList(gameInfos);
    private final ListProperty<GameInfo> gameInfosProperty = new SimpleListProperty<>(gameInfoObservableList);

    public GameSelectionViewModel() {
        model.getGames().forEach(game -> gameInfoObservableList.add(converter.toGameInfo(game)));
    }

    public ListProperty<GameInfo> gameInfosPropertyProperty() {
        return gameInfosProperty;
    }
}
