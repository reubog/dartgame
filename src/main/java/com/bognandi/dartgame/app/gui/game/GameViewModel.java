package com.bognandi.dartgame.app.gui.game;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class GameViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(GameViewModel.class);

    @Autowired
    private Notifications notifications;

    @Autowired
    private GameModel model;

    private DartValuePropertyWrapper dartValue1 = new DartValuePropertyWrapper(new SimpleStringProperty(), new SimpleStringProperty());
    private DartValuePropertyWrapper dartValue2 = new DartValuePropertyWrapper(new SimpleStringProperty(), new SimpleStringProperty());
    private DartValuePropertyWrapper dartValue3 = new DartValuePropertyWrapper(new SimpleStringProperty(), new SimpleStringProperty());

    private Map<String,Player> playerNameMap = new LinkedHashMap<>();
    private ListProperty<Player> playerList = new SimpleListProperty<>();
    private ListProperty<Round> roundList = new SimpleListProperty<>();

    private StringProperty score = new SimpleStringProperty();

    private BooleanProperty waitingForPlayers = new SimpleBooleanProperty();

    private Round lastRound;
    private Player currentGuiPlayer;

    public GameViewModel() {
        initSubscriptions();
    }

    private void initSubscriptions() {
        notifications.subscribe(Notifications.WAITING_FOR_PLAYERS, this, event -> {
            LOG.debug("Waiting for players");
            waitingForPlayers.setValue(true);
        });

        notifications.subscribe(Notifications.PLAYER_ADDED, this, event -> {
            List<com.bognandi.dartgame.domain.dartgame.Player> newPlayers = model.getPlayers();
            newPlayers.removeAll(playerList);

           LOG.debug("{} Player(s) added", newPlayers.size());
           playerListProperty().addAll(newPlayers.stream().map(this::toGuiPlayer).toList());
        });

        notifications.subscribe(Notifications.ROUND_STARTED, this, event -> {
            LOG.debug("Round started");

            roundListProperty().add(lastRound = new Round(
               new SimpleStringProperty(model.getCurrentRound() + ""),
               new SimpleStringProperty("0")
            ));

            dartValue1Property().value().setValue("-");
            dartValue2Property().value().setValue("-");
            dartValue3Property().value().setValue("-");

            dartValue1Property().multiplier().setValue("");
            dartValue2Property().multiplier().setValue("");
            dartValue3Property().multiplier().setValue("");
        });

        notifications.subscribe(Notifications.PLAYER_TURN, this, event -> {
            LOG.debug("Player turn");
            currentGuiPlayer = playerNameMap.get(model.getCurrentPlayer().getName());
            playerListProperty().forEach(player ->
                    player.currentPlayerProperty().setValue(player == currentGuiPlayer));
        });

        notifications.subscribe(Notifications.DART_THROWN, this, event -> {
            LOG.debug("Dart thrown");
            currentGuiPlayer.scoreProperty().setValue(model.getCurrentPlayerScore() + "");
            lastRound.score().setValue(model.getCurrentPlayerRoundScore() + "");

//            dartValue1Property().value().setValue(model.);setDartValue(model.getCurrentDartValue());
//            dartValue2Property().setDartValue(model.getCurrentDartValue());
//            dartValue3Property().setDartValue(model.getCurrentDartValue());
        });

    }

    public DartValuePropertyWrapper dartValue1Property() {
        return dartValue1;
    }

    public DartValuePropertyWrapper dartValue2Property() {
        return dartValue2;
    }

    public DartValuePropertyWrapper dartValue3Property() {
        return dartValue3;
    }

    public ListProperty<Player> playerListProperty() {
        return playerList;
    }

    public ListProperty<Round> roundListProperty() {
        return roundList;
    }

    public StringProperty scoreProperty() {
        return score;
    }

    public BooleanProperty waitingForPlayersProperty() {
        return waitingForPlayers;
    }

    private Player toGuiPlayer(com.bognandi.dartgame.domain.dartgame.Player player) {
        Player gui = new Player(
                new SimpleStringProperty(player.getName()),
                new SimpleStringProperty(0 + ""),
                new SimpleBooleanProperty(false));
        playerNameMap.put(player.getName(), gui);
        return gui;
    }
}
