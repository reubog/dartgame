package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        scoreBoard.onPlayersSet(dartGame, List.of(player1, player2));
        scoreBoard.onWaitingForPlayers(dartGame);
        scoreBoard.onGamePlayStarted(dartGame);
        scoreBoard.onRoundStarted(dartGame, 1);

    }

    @Test
    void test() {
        Mockito.when(dartValueMapper.getDartScore(Dart.ONE)).thenReturn(1);
        scoreBoard.onPlayerTurn(dartGame, 1, player1);
        scoreBoard.onDartThrown(dartGame, Dart.ONE);

        PlayerScore playerScore =  scoreBoard.getPlayerScore(player1);
        Assertions.assertEquals(300, playerScore.getScore() );
        Assertions.assertEquals(1, playerScore.getPlayedRounds() );
        Assertions.assertEquals(1, playerScore.getThrownDarts().size() );
    }
}
