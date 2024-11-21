package com.bognandi.dartgame.app.gui.game;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayersPaneComponent extends HBox {

    private List<Player> players = new ArrayList<>();
    private ObservableList<Player> playerComponents = FXCollections.observableList(players);
    private Map<Player,PlayerComponent> playerComponentMap = new LinkedHashMap<>();

    public PlayersPaneComponent() {
        playerComponents.addListener(ListChangeListener);
    }

    public void bindPlayers(ObservableList<Player> observableList) {
        Bindings.bindContent(players, observableList);
    }

    private ListChangeListener<Player> ListChangeListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                change.getAddedSubList().forEach(this::addPlayer);
            }
            if (change.wasRemoved()) {
                change.getRemoved().forEach(this::removePlayer);
            }
        }
    };

    private void addPlayer(Player player) {
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.bindPlayer(player);
        playerComponentMap.put(player, playerComponent);
        getChildren().add(playerComponent);
    }

    private void removePlayer(Player player) {
        getChildren().remove(playerComponentMap.get(player));
    }
}
