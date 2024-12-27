package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class X01ScoreBoard implements ScoreBoard, DartgameEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(X01ScoreBoard.class);

    private final int startScore;
    private int currentPlayerTurnStartScore;
    private Player currentPlayer;
    private final DartValueMapper dartValueMapper;
    private Map<Player, X01PlayerScore> playerScoreMap = new LinkedHashMap<>();
    private List<Player> playing = new ArrayList<>();


    public X01ScoreBoard(int startScore, DartValueMapper dartValueMapper) {
        this.startScore = startScore;
        this.dartValueMapper = dartValueMapper;
    }

    @Override
    public int getMinimumNumberOfPlayers() {
        return 2;
    }

    @Override
    public boolean isBust(Player player) {
        return playerScoreMap.get(currentPlayer).getScore() < 0;
    }

    @Override
    public boolean isWinner(Player player) {
        return playerScoreMap.get(currentPlayer).getScore() == 0;
    }

    @Override
    public PlayerScore getPlayerScore(Player player) {
        return playerScoreMap.get(player);
    }

    @Override
    public Player getLeadingPlayer() {
        return getPlayerPosition().get(0);
    }

    @Override
    public List<Player> getPlayerPosition() {
        return playerScoreMap.keySet().stream()
                .filter(player -> playing.contains(player))
                .sorted((o1, o2) -> playerScoreMap.get(o1).getScore() < playerScoreMap.get(o2).getScore() ? -1 : 1)
                .toList();
    }

    public void onWaitingForPlayers(Dartgame dartGame) {
        playing.clear();
    }

    @Override
    public void onGamePlayStarted(Dartgame dartGame) {

    }

    @Override
    public void onGameFinished(Dartgame dartGame) {

    }

    @Override
    public void onPlayersSet(Dartgame dartGame, List<Player> players) {
        LOG.debug("players={}", players.stream().map(Player::getName).collect(Collectors.joining(", ")));

        players.forEach(player -> playerScoreMap.put(player, new X01PlayerScore(startScore)));

        playing.clear();
        playing.addAll(players);
    }

    @Override
    public void onRoundStarted(Dartgame dartGame, int roundNumber) {
        playerScoreMap.values().forEach(X01PlayerScore::nextRound);
    }

    @Override
    public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
        currentPlayer = player;
        currentPlayerTurnStartScore = playerScoreMap.get(currentPlayer).getScore();
    }

    @Override
    public void onDartThrown(Dartgame dartGame, Dart dart) {
        playerScoreMap.get(currentPlayer)
                .decreaseScoreWith(dartValueMapper.getDartScore(dart))
                .nextDart(dart);
    }

    @Override
    public void onRemoveDarts(Dartgame dartGame, int round, Player player) {

    }

    @Override
    public void onPlayerWon(Dartgame dartGame, Player player) {
        playing.remove(player);
    }

    @Override
    public void onPlayerBust(Dartgame dartGame, Player player) {
        playerScoreMap.get(currentPlayer).setScore(currentPlayerTurnStartScore);
    }

    @Override
    public void onPlayerLost(Dartgame dartGame, Player player) {
        playing.remove(player);
    }

}
