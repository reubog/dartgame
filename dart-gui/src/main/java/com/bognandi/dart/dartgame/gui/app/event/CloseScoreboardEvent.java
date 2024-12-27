package com.bognandi.dart.dartgame.gui.app.event;

import com.bognandi.dart.core.dartgame.ScoreBoard;
import org.springframework.context.ApplicationEvent;

public class CloseScoreboardEvent extends ApplicationEvent {

    public CloseScoreboardEvent(ScoreBoard scoreBoard) {
        super(scoreBoard);
    }
}