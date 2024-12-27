package com.bognandi.dart.dartgame.gui.app.event;

import com.bognandi.dart.core.dartgame.Player;
import com.bognandi.dart.core.dartgame.ScoreBoard;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class OpenScoreboardEvent extends ApplicationEvent {

    private ScoreBoard scoreboard;
    private List<Player> players;

    public OpenScoreboardEvent(List<Player> players, ScoreBoard scoreBoard) {
        super(scoreBoard);
        this.players = players;
        this.scoreboard = scoreBoard;
    }

    public ScoreBoard getScoreboard() {
        return scoreboard;
    }

    public List<Player> getPlayers() {
        return players;
    }
}