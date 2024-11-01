package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bognandi.dartgame.domain.game.Dart.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class X01GameTest {

    private DartGame game = new X01Game(301);

    @Mock
    private GameEventListener gameEventListener;

    private DefaultDartBoardAction dartBoardAction = new DefaultDartBoardAction();

    @Mock
    private ScoreBoard scoreBoard;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private Player player3;

    @BeforeEach
    void setUp() {
        game.startGame(dartBoardAction, scoreBoard);
        game.addEventListener(gameEventListener);

    }

    @Test
    void testGamePlay() {
        game.addPlayer(player1);
        game.addPlayer(player2);

        verify(gameEventListener).onPlayerAdded(game, player1);
        verify(gameEventListener).onPlayerAdded(game, player2);

        dartBoardAction.doPressButton();

        verify(gameEventListener).onGameStarted(game);

        verify(gameEventListener).onRoundStarted(game, 1);
        verify(gameEventListener).onPlayerTurn(game, 1, player1);
        dartBoardAction.doThrowDart(ONE);
        verify(gameEventListener).onDartThrown(game, ONE);
        dartBoardAction.doThrowDart(TWO);
        verify(gameEventListener).onDartThrown(game, TWO);
        dartBoardAction.doThrowDart(THREE);
        verify(gameEventListener).onDartThrown(game, THREE);
        verify(gameEventListener).onRemoveDarts(game, 1, player1);
        dartBoardAction.doThrowDart(FOUR);
        verify(gameEventListener, never()).onDartThrown(game, FOUR);
        dartBoardAction.doPressButton();
        verify(gameEventListener).onPlayerTurn(game, 1, player2);
        dartBoardAction.doThrowDart(FIVE);
        verify(gameEventListener).onDartThrown(game, FIVE);
        dartBoardAction.doPressButton();
        verify(gameEventListener).onRemoveDarts(game, 1, player2);
        dartBoardAction.doThrowDart(SIX);
        verify(gameEventListener, never()).onDartThrown(game, SIX);
        dartBoardAction.doPressButton();

        verify(gameEventListener).onRoundStarted(game, 2);
        verify(gameEventListener).onPlayerTurn(game, 2, player1);
        dartBoardAction.doPressButton();
        verify(gameEventListener).onPlayerTurn(game, 2, player2);
        dartBoardAction.doPressButton();


    }

    @Test
    void testWinAndGameFinished() {
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        dartBoardAction.doPressButton();

        // player 1
        throw3dartsAndPressButton(DOUBLE_BULLSEYE, DOUBLE_BULLSEYE, DOUBLE_BULLSEYE);

        // player 2
        throw3dartsAndPressButton(DOUBLE_BULLSEYE, DOUBLE_BULLSEYE, DOUBLE_BULLSEYE);

        // player 3
        throw3dartsAndPressButton(DOUBLE_BULLSEYE, DOUBLE_BULLSEYE, DOUBLE_BULLSEYE);


        // player 1
        throw3dartsAndPressButton(DOUBLE_BULLSEYE, DOUBLE_BULLSEYE, DOUBLE_BULLSEYE);

        // player 2
        throw3dartsAndPressButton(DOUBLE_BULLSEYE,DOUBLE_BULLSEYE,DOUBLE_TWENTY);

        // player 3
        throw3dartsAndPressButton(THREE,THREE,THREE);

        // player 1
        when(scoreBoard.isWinner(player1)).thenReturn(true);
        dartBoardAction.doThrowDart(ONE);
        verify(gameEventListener).onPlayerWon(game, player1);
        dartBoardAction.doPressButton();

        // player 2
        verify(gameEventListener).onPlayerTurn(game, 3, player2);
        when(scoreBoard.isWinner(player2)).thenReturn(true);
        dartBoardAction.doThrowDart(ELEVEN);
        verify(gameEventListener).onPlayerWon(game, player2);

        verify(gameEventListener).onGameFinished(game);
    }

    @Test
    void testSinglePlay() {
        game.addPlayer(player1);
        when(scoreBoard.isWinner(player1)).thenReturn(true);

        dartBoardAction.doPressButton();
        dartBoardAction.doThrowDart(ONE);

        verify(gameEventListener).onPlayerWon(game, player1);
        verify(gameEventListener).onGameFinished(game);
    }

    @Test
    void testStartGameWithoutPlayerShouldNotStart() {
        when(scoreBoard.getMinimumNumberOfPlayers()).thenReturn(2);
        game.addPlayer(player1);
        dartBoardAction.doPressButton();
        verify(gameEventListener, never()).onGameStarted(game);
    }

    @Test
    void testBust() {
        game.addPlayer(player1);
        when(scoreBoard.isBust(player1)).thenReturn(true);

        dartBoardAction.doPressButton();
        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);

        verify(gameEventListener).onPlayerBust(game, player1);
        verify(gameEventListener).onGameFinished(game);
    }

    private void throw3dartsAndPressButton(Dart first, Dart second, Dart third) {
        dartBoardAction.doThrowDart(first);
        dartBoardAction.doThrowDart(second);
        dartBoardAction.doThrowDart(third);
        dartBoardAction.doPressButton();
    }
}
