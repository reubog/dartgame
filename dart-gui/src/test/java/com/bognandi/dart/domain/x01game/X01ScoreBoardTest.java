package com.bognandi.dart.domain.x01game;

import com.bognandi.dart.core.dartgame.Dartgame;
import com.bognandi.dart.core.dartgame.DartValueMapper;
import com.bognandi.dart.core.dartgame.Player;
import com.bognandi.dart.core.dartgame.PlayerScore;
import com.bognandi.dart.dartgame.x01game.x01game.X01ScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bognandi.dart.core.dartgame.Dart.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class X01ScoreBoardTest {

    private X01ScoreBoard scoreBoard;

    @Mock
    private Dartgame dartGame;

    @Mock
    private DartValueMapper dartValueMapper;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @BeforeEach
    void setUp() {
        scoreBoard = new X01ScoreBoard(301, dartValueMapper);
        scoreBoard.onPlayerAdded(dartGame, player1);
        scoreBoard.onPlayerAdded(dartGame, player2);
        scoreBoard.onGameStarting(dartGame);
        scoreBoard.onGameStarted(dartGame);
        scoreBoard.onRoundStarted(dartGame, 1);

    }

    @Test
    void test() {
        when(dartValueMapper.getDartScore(ONE)).thenReturn(1);
        scoreBoard.onPlayerTurn(dartGame, 1, player1);
        scoreBoard.onDartThrown(dartGame, ONE);

        PlayerScore playerScore =  scoreBoard.getPlayerScore(player1);
        assertEquals(300, playerScore.getScore() );
        assertEquals(1, playerScore.getPlayedRounds() );
        assertEquals(1, playerScore.getThrownDarts() );
    }
}
