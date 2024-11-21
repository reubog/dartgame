package com.bognandi.dartgame.app.gui.gameselection;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class GameSelectionViewModel {

    @Autowired
    private GameSelectionModel model;

    private final List<GameInfo> gameInfos = new ArrayList<>();
    private final ObservableList<GameInfo> gameInfoObservableList = FXCollections.observableList(gameInfos);
    private final ListProperty<GameInfo> gameInfosProperty = new SimpleListProperty<>(gameInfoObservableList);

    public GameSelectionViewModel() {
       gameInfoObservableList.addAll(model.getGames());
    }

    public ListProperty<GameInfo> gameInfosPropertyProperty() {
        return gameInfosProperty;
    }

    public void onSelectGame(GameInfo gameInfo) {

    }
}
