package com.bognandi.dartgame.app.gui.game;

import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RoundsPaneComponent extends VBox {

    private List<RoundComponent> roundComponents = new ArrayList<>();
    private ObservableList<RoundComponent> roundComponentObservableList = FXCollections.observableList(roundComponents);
    private Map<Round, RoundComponent> roundsMap = new LinkedHashMap<>();

    public void bindRounds(ListProperty<Round> roundListProperty) {
        roundListProperty.addListener((observableList, old, rounds) -> {
            rounds.forEach(round -> {
                RoundComponent roundComponent = new RoundComponent();
                roundComponent.bindRound(round);
                roundComponentObservableList.add(roundComponent);
            });
        });
    }

    private void addRound(Round round) {
        RoundComponent component = new RoundComponent();
        component.bindRound(round);
        roundsMap.put(round, component);
        getChildren().add(component);
    }

    private void removeRound(Round round) {
        RoundComponent roundComponent = roundsMap.get(round);
        getChildren().remove(roundComponent);
    }

    private javafx.collections.ListChangeListener<Round> ListChangeListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                change.getAddedSubList().forEach(this::addRound);
            }
            if (change.wasRemoved()) {
                change.getRemoved().forEach(this::removeRound);
            }
        }
    };
}
