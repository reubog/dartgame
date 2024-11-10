package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.dartgame.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class X01DartGame implements DartGame, DartBoardEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(X01DartGame.class);

    private enum GameState {
        WAITING_FOR_PLAYERS,
        STARTED,
        FINISHED
    }

    private enum PlayerState {
        WINNER,
        PLAYING
    }

    private ScoreBoard scoreBoard;
    private GameState gameState;
    private List<DartGameEventListener> dartGameEventListeners = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private int round;
    private boolean enableDarts;
    private Map<Player, PlayerState> playerStateMap;
    private List<Dart> thrownDarts = new ArrayList<>();

    @Override
    public String getName() {
        return "301";
    }

    @Override
    public void startGame(ScoreBoard scoreBoard) {
        LOG.info("Starting game");

        this.scoreBoard = scoreBoard;
        this.dartGameEventListeners.addFirst((X01ScoreBoard) scoreBoard);
        this.playerStateMap = new LinkedHashMap<>();
        this.gameState = GameState.WAITING_FOR_PLAYERS;

        LOG.info("Game starting, waiting for players and button press");
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onGameStarting(this));
    }

    @Override
    public void addEventListener(DartGameEventListener listener) {
        dartGameEventListeners.add(listener);
    }

    @Override
    public void removeEventListener(DartGameEventListener listener) {
        dartGameEventListeners.remove(listener);
    }

    @Override
    public void onDartThrown(Dart dart) {
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
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onDartThrown(this, dart));

        if (scoreBoard.isBust(player)) {
            playerBust(player);
        } else if (scoreBoard.isWinner(player)) {
            playerWon(player);
        } else if (thrownDarts.size() == 3) {
            removeDarts();
        }
    }

    @Override
    public void onButton() {
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
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onGameStarted(this));

        newRound();
    }

    private boolean isAnyDartThrown() {
        return enableDarts && !thrownDarts.isEmpty();
    }

    private void playerBust(Player player) {
        enableDarts = false;

        LOG.info("Player {} busted!", player);
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerBust(this, player));

        if (isNoMoreActivePlayers()) {
            finishGame();
        }
    }

    private void playerWon(Player player) {
        playerStateMap.put(player, PlayerState.WINNER);
        enableDarts = false;

        LOG.info("Player {} won!", player);
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerWon(this, player));

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
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onGameFinished(this));
    }

    private void removeDarts() {
        enableDarts = false;
        Player player = getCurrentPlayer();
        LOG.info("Player {}: Remove darts from round {}", player, round);
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onRemoveDarts(this, round, player));
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
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerAdded(this, player));
    }

    private void newRound() {
        round++;
        currentPlayerIndex = -1;
        LOG.info("Round {} is now starting", round);
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onRoundStarted(this, round));
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
        this.dartGameEventListeners.forEach((dartGameNotification) -> dartGameNotification.onPlayerTurn(this, round, player));
    }

    @Override
    public PlayerScore getPlayerScore(Player player) {
        return scoreBoard.getPlayerScore(player);
    }
}
