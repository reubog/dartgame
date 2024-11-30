package com.bognandi.dart.dartgame.x01game.x01game;

import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardValueMapper;
import com.bognandi.dart.core.dartgame.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class X01Dartgame implements Dartgame {

    private static final Logger LOG = LoggerFactory.getLogger(X01Dartgame.class);

    private enum GameState {
        WAITING_FOR_PLAYERS,
        STARTED,
        FINISHED
    }

    private enum PlayerState {
        WINNER,
        PLAYING
    }

    private final DartboardValueMapper dartboardValueMapper = new DartboardValueMapper();
    private final X01ScoreBoard scoreBoard;
    private GameState gameState;
    private List<DartgameEventListener> dartgameEventListeners = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private int round;
    private boolean enableDarts;
    private Map<Player, PlayerState> playerStateMap;
    private List<Dart> thrownDarts = new ArrayList<>();
    private Dartboard dartboard;

    public X01Dartgame(X01ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @Override
    public void attachDartboard(Dartboard dartboard) {
        this.dartboard = dartboard;
        this.dartboard.setOnDartboardValue(this::onDartboardValue);
    }

    @Override
    public void startGame() {
        LOG.info("Starting game");

        this.dartgameEventListeners.addFirst(scoreBoard);
        this.playerStateMap = new LinkedHashMap<>();
        this.gameState = GameState.WAITING_FOR_PLAYERS;

        LOG.info("Game starting, waiting for players and button press");
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onGameStarting(this));
    }

    @Override
    public void addEventListener(DartgameEventListener listener) {
        dartgameEventListeners.add(listener);
    }

    @Override
    public void removeEventListener(DartgameEventListener listener) {
        dartgameEventListeners.remove(listener);
    }

    private void onDartboardValue(DartboardValue value) {
        switch (value) {
            case INITIAL_CODE -> {
            }
            case RED_BUTTON -> onButton();

            default -> onDartThrown(DartboardValueMapper.DART_MAP.get(value));
        }
    }

    private void onDartThrown(Dart dart) {
        if (GameState.FINISHED.equals(gameState)) {
            LOG.warn("Game is finished, no more darts can be thrown");
            return;
        } else if (!enableDarts) {
            LOG.warn("Darts are suspended and will not be registered");
            return;
        }

        Player player = getCurrentPlayer();
        thrownDarts.add(dart);
        LOG.info("Dart #{} was {}", thrownDarts.size(), dart);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onDartThrown(this, dart));

        if (scoreBoard.isBust(player)) {
            playerBust(player);
        } else if (scoreBoard.isWinner(player)) {
            playerWon(player);
        } else if (thrownDarts.size() == 3) {
            removeDarts();
        }
    }

    private void onButton() {
        LOG.info("Button was pressed");
        if (GameState.FINISHED.equals(gameState)) {
            LOG.warn("Game is finished, but button was pressed, nothing to do");
        } else if (GameState.WAITING_FOR_PLAYERS.equals(gameState)) {
            startPlaying();
        } else if (isAnyDartThrown()) {
            removeDarts();
        } else if (isLastPlayerOfRound()) {
            newRound();
        } else {
            nextPlayerTurn();
        }
    }

    private void startPlaying() {
        if (players.size() < scoreBoard.getMinimumNumberOfPlayers()) {
            LOG.warn("Not enough players to start the game");
            return;
        }

        round = 0;
        gameState = GameState.STARTED;
        players.forEach(player -> playerStateMap.put(player, PlayerState.PLAYING));

        LOG.info("Game Started");
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onGameStarted(this));

        newRound();
    }

    private boolean isAnyDartThrown() {
        return enableDarts && !thrownDarts.isEmpty();
    }

    private void playerBust(Player player) {
        enableDarts = false;

        LOG.info("Player {} busted!", player);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerBust(this, player));

        if (isNoMoreActivePlayers()) {
            finishGame();
        }
    }

    private void playerWon(Player player) {
        playerStateMap.put(player, PlayerState.WINNER);
        enableDarts = false;

        LOG.info("Player {} won!", player);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerWon(this, player));

        if (isNoMoreActivePlayers()) {
            finishGame();
        }
    }

    private boolean isNoMoreActivePlayers() {
        int activePlayers = (int) playerStateMap.values().stream()
                .filter(state -> PlayerState.PLAYING.equals(state))
                .count();
        return activePlayers < scoreBoard.getMinimumNumberOfPlayers();
    }

    private void finishGame() {
        gameState = GameState.FINISHED;
        LOG.info("Game finished");
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onGameFinished(this));
    }

    private void removeDarts() {
        enableDarts = false;
        Player player = getCurrentPlayer();
        LOG.info("Player {}: Remove darts from round {}", player, round);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onRemoveDarts(this, round, player));
    }

    private boolean isLastPlayerOfRound() {
        return currentPlayerIndex == players.size() - 1;
    }


    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    @Override
    public void addPlayer(Player player) {
        if (!GameState.WAITING_FOR_PLAYERS.equals(gameState)) {
            LOG.warn("Game is not waiting for players, so ignoring");
            return;
        }

        players.add(player);
        LOG.info("Added player {}", player);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerAdded(this, player));
    }

    private void newRound() {
        round++;
        currentPlayerIndex = -1;
        LOG.info("Round {} is now starting", round);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onRoundStarted(this, round));
        nextPlayerTurn();
    }

    private void nextPlayerTurn() {
        while (currentPlayerIndex < players.size()) {
            currentPlayerIndex++;
            Player player = players.get(currentPlayerIndex);
            if (PlayerState.PLAYING.equals(playerStateMap.get(player))) {
                break;
            }
        }

        boolean isLastPlayer = currentPlayerIndex == players.size();
        if (isLastPlayer) {
            newRound();
        }

        thrownDarts.clear();
        enableDarts = true;
        Player player = getCurrentPlayer();
        LOG.info("Starting turn for player {}", player);
        this.dartgameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerTurn(this, round, player));
    }

    @Override
    public PlayerScore getPlayerScore(Player player) {
        return scoreBoard.getPlayerScore(player);
    }

    @Override
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
