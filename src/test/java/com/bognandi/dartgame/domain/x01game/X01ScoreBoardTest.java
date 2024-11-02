package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.DartGame;
import com.bognandi.dartgame.domain.game.DartValueMapper;
import com.bognandi.dartgame.domain.game.Player;
import com.bognandi.dartgame.domain.game.PlayerScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bognandi.dartgame.domain.game.Dart.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class X01ScoreBoardTest {

    private X01ScoreBoard scoreBoard;

    @Mock
    private DartGame dartGame;

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
        when(dartValueMapper.getDartValue(ONE)).thenReturn(1);
        scoreBoard.onPlayerTurn(dartGame, 1, player1);
        scoreBoard.onDartThrown(dartGame, ONE);

        PlayerScore playerScore =  scoreBoard.getPlayerScore(player1);
        assertEquals(300, playerScore.getScore() );
        assertEquals(1, playerScore.getPlayedRounds() );
        assertEquals(1, playerScore.getThrownDarts() );
    }
}
