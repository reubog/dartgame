package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RequiredArgsConstructor
public class X01Game implements DartGame {

    private static final Logger LOG = LoggerFactory.getLogger(X01Game.class);

    private ScoreBoard scoreBoard;
    private X01GameState gameState;
    private final int startScore;
    private List<DartGameNotification> dartGameNotifications = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private int round;
    private boolean enableDarts;
    private Map<Player, X01PlayerState> playerStateMap;
    private List<Dart> thrownDarts = new ArrayList<>();

    @Override
    public void initGame(DartBoardAction action, ScoreBoard scoreBoard, DartGameNotification... dartGameNotifications) {
        LOG.info("Initializing game");

        action.onDartThrown(this::dartWasThrown);
        action.onButton(this::buttonWasPressed);
        LOG.info("DartBoard actions bound");

        this.scoreBoard = scoreBoard;
        this.dartGameNotifications.addAll(Arrays.asList(dartGameNotifications));
        this.playerStateMap = new LinkedHashMap<>();
        this.gameState = X01GameState.WAITING_FOR_PLAYERS;

        LOG.info("Game Initialized, waiting for players and button press");
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onGameInitialized(this));
    }

    @Override
    public void startGame() {
        if (players.size() < 1) {
            LOG.warn("Not enough players to start the game");
            return;
        }

        LOG.info("Starting game");
        round = 0;
        gameState = X01GameState.STARTED;
        players.forEach(player -> playerStateMap.put(player, X01PlayerState.PLAYING));
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onGameStarted(this));
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
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onDartThrown(this, dart));

        if (scoreBoard.isBust(player)) {
            playerBust(player);
        }
        else if (scoreBoard.isWinner(player)) {
            playerWon(player);
        } else if (thrownDarts.size() == 3) {
            removeDarts();
        }
    }

    private void playerBust(Player player) {
        enableDarts = false;

        LOG.info("Player {} busted!", player);
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onPlayerBust(this, player));

        if (numberOfPlayersStillPlaying() < 2) {
            finishGame();
        }
    }

    private void playerWon(Player player) {
        playerStateMap.put(player, X01PlayerState.WINNER);
        enableDarts = false;

        LOG.info("Player {} won!", player);
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onPlayerWon(this, player));

        if (numberOfPlayersStillPlaying() < 2) {
            finishGame();
        }
    }

    private void finishGame() {
        gameState = X01GameState.FINISHED;
        LOG.info("Game finished");
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onGameFinished(this));
    }

    private int numberOfPlayersStillPlaying() {
        return (int) playerStateMap.values().stream()
                .filter(state -> X01PlayerState.PLAYING.equals(state))
                .count();
    }

    private void removeDarts() {
        enableDarts = false;
        Player player = getCurrentPlayer();
        LOG.info("Player {}: Remove darts from round {}", player, round);
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onRemoveDarts(this, round, player));
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
        X01PlayerScore playerScore = new X01PlayerScore(startScore);
        this.dartGameNotifications.add(playerScore);
        LOG.info("Added player {}", player);
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onPlayerAdded(this, player));
    }

    private void newRound() {
        round++;
        currentPlayerIndex = -1;
        LOG.info("Round {} is now starting", round);
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onRoundStarted(this, round));
        nextPlayerTurn();
    }

    private void nextPlayerTurn() {
        boolean isLastPlayer = currentPlayerIndex == players.size() - 1;
        if (isLastPlayer) {
            newRound();
        }

        thrownDarts.clear();
        currentPlayerIndex++;
        enableDarts = true;
        Player player = getCurrentPlayer();
        LOG.info("Starting turn for player {}", player);
        this.dartGameNotifications.forEach((dartGameNotification) -> dartGameNotification.onPlayerTurn(this, round, player));
    }
}
