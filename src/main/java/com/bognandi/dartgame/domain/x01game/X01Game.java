package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class X01Game implements DartGame {

    private static final Logger LOG = LoggerFactory.getLogger(X01Game.class);

    private X01GameState gameState;
    private final int startScore;
    private Notifyer notifyer;
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private int round;
    private boolean enableDarts;
    private Map<Player, X01PlayerScore> scoreMap;
    private Map<Player, X01PlayerState> playerStateMap;
    private List<Dart> thrownDarts = new ArrayList<>();

    @Override
    public void initGame(DartBoardAction action, Notifyer notifyer) {
        LOG.info("Initializing game");

        action.onDartThrown(this::dartWasThrown);
        action.onButton(this::buttonWasPressed);
        LOG.info("DartBoard actions bound");

        this.notifyer = notifyer;
        this.scoreMap = new LinkedHashMap<>();
        this.playerStateMap = new LinkedHashMap<>();
        this.gameState = X01GameState.WAITING_FOR_PLAYERS;

        LOG.info("Game Initialized, waiting for players and button press");
        notifyer.onGameInitialized(this);
    }

    @Override
    public void startGame() {
        if (players.size() < 2) {
            LOG.warn("Not enough players to start the game");
            return;
        }

        LOG.info("Starting game");
        round = 0;
        gameState = X01GameState.STARTED;
        players.forEach(player -> playerStateMap.put(player, X01PlayerState.PLAYING));
        notifyer.onGameStarted(this);
        newRound();
    }

    private void buttonWasPressed(Void unused) {
        LOG.info("Button was pressed");
        if (X01GameState.WAITING_FOR_PLAYERS.equals(gameState)) {
            startGame();
        } else if (isAnyDartThrown()) {
            removeDarts();
        } else if (isLastPlayerOfRound()) {
            newRound();
        } else {
            nextPlayerTurn();
        }
    }

    private boolean isAnyDartThrown() {
        return enableDarts && thrownDarts.size() > 0;
    }

    private void dartWasThrown(Dart dart) {
        if (!enableDarts) {
            LOG.warn("Darts are suspended and will not be registered");
            return;
        }

        Player player = getCurrentPlayer();
        thrownDarts.add(dart);
        LOG.info("Dart #{} was {}", thrownDarts.size(), dart);
        notifyer.onDartThrown(this, dart);

        scoreMap.get(player).addThrownDart(dart);

        if (isPlayerWon(player)) {
            playerWon(player);
        } else if (thrownDarts.size() == 3) {
            removeDarts();
        }
    }

    private void playerWon(Player player) {
        playerStateMap.put(player, X01PlayerState.WINNER);

        LOG.info("Player {} won!", player);
        notifyer.onPlayerWon(this, player);

        if (numberOfPlayersStillPlaying() < 2) {
            finishGame();
        }
        else {
            removeDarts();
        }
    }

    private void finishGame() {
        gameState = X01GameState.FINISHED;
        LOG.info("Game finished");
        notifyer.onGameFinished(this);
    }

    private int numberOfPlayersStillPlaying() {
        return (int) playerStateMap.values().stream()
                .filter(state -> X01PlayerState.PLAYING.equals(state))
                .count();
    }

    private boolean isPlayerWon(Player player) {
        return scoreMap.get(player).getTotalScore() == 0;
    }

    private void removeDarts() {
        enableDarts = false;
        Player player = getCurrentPlayer();
        LOG.info("Player {}: Remove darts from round {}", player, round);
        notifyer.onRemoveDarts(this, round, player);
    }

    private boolean isLastPlayerOfRound() {
        return currentPlayerIndex == players.size() - 1;
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    @Override
    public void addPlayer(Player player) {
        if (!X01GameState.WAITING_FOR_PLAYERS.equals(gameState)) {
            LOG.warn("Game is already started, cannot add player");
            return;
        }

        players.add(player);
        scoreMap.put(player, new X01PlayerScore(startScore));
        LOG.info("Added player {}. Calling onPlayerAdded", player);
        notifyer.onPlayerAdded(this, player);
    }

    private void newRound() {
        round++;
        currentPlayerIndex = -1;
        LOG.info("Round {} is now starting. Calling onRoundStarted", round);
        notifyer.onRoundStarted(this, round);
        nextPlayerTurn();
    }

    private void nextPlayerTurn() {
        boolean isLastPlayer = currentPlayerIndex == players.size() - 1;
        if (isLastPlayer) {
            newRound();
        }

        thrownDarts.clear();
        enableDarts = true;
        Player player = players.get(++currentPlayerIndex);
        LOG.info("Starting turn for player {}", player);
        notifyer.onPlayerTurn(this, round, player);
    }
}
