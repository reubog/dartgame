package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
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
    private DartGameNotification dartGameNotification;

    private DefaultDartBoardAction dartBoardAction = new DefaultDartBoardAction();

    @Mock
    private ScoreBoard scoreBoard;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private Player player3;

    @Test
    void testGamePlay() {
        game.initGame(dartBoardAction, scoreBoard, dartGameNotification);
        game.addPlayer(player1);
        game.addPlayer(player2);

        verify(dartGameNotification).onPlayerAdded(game, player1);
        verify(dartGameNotification).onPlayerAdded(game, player2);

        dartBoardAction.doPressButton();

        verify(dartGameNotification).onGameStarted(game);

        verify(dartGameNotification).onRoundStarted(game, 1);
        verify(dartGameNotification).onPlayerTurn(game, 1, player1);
        dartBoardAction.doThrowDart(ONE);
        verify(dartGameNotification).onDartThrown(game, ONE);
        dartBoardAction.doThrowDart(TWO);
        verify(dartGameNotification).onDartThrown(game, TWO);
        dartBoardAction.doThrowDart(THREE);
        verify(dartGameNotification).onDartThrown(game, THREE);
        verify(dartGameNotification).onRemoveDarts(game, 1, player1);
        dartBoardAction.doThrowDart(FOUR);
        verify(dartGameNotification, never()).onDartThrown(game, FOUR);
        dartBoardAction.doPressButton();
        verify(dartGameNotification).onPlayerTurn(game, 1, player2);
        dartBoardAction.doThrowDart(FIVE);
        verify(dartGameNotification).onDartThrown(game, FIVE);
        dartBoardAction.doPressButton();
        verify(dartGameNotification).onRemoveDarts(game, 1, player2);
        dartBoardAction.doThrowDart(SIX);
        verify(dartGameNotification, never()).onDartThrown(game, SIX);
        dartBoardAction.doPressButton();

        verify(dartGameNotification).onRoundStarted(game, 2);
        verify(dartGameNotification).onPlayerTurn(game, 2, player1);
        dartBoardAction.doPressButton();
        verify(dartGameNotification).onPlayerTurn(game, 2, player2);
        dartBoardAction.doPressButton();


    }

    @Test
    void testWinAndGameFinished() {


        game.initGame(dartBoardAction, scoreBoard, dartGameNotification);
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
        verify(dartGameNotification).onPlayerWon(game, player1);
        dartBoardAction.doPressButton();

        // player 2
        verify(dartGameNotification).onPlayerTurn(game, 3, player2);
        when(scoreBoard.isWinner(player2)).thenReturn(true);
        dartBoardAction.doThrowDart(ELEVEN);
        verify(dartGameNotification).onPlayerWon(game, player2);

        verify(dartGameNotification).onGameFinished(game);
    }

    @Test
    void testSinglePlay() {
        when(scoreBoard.isWinner(player1)).thenReturn(true);

        game.initGame(dartBoardAction, scoreBoard, dartGameNotification);
        game.addPlayer(player1);
        dartBoardAction.doPressButton();
        dartBoardAction.doThrowDart(ONE);

        verify(dartGameNotification).onPlayerWon(game, player1);
        verify(dartGameNotification).onGameFinished(game);
    }

    @Test
    void testStartGameWithoutPlayserShouldNotStart() {
        game.initGame(dartBoardAction, scoreBoard, dartGameNotification);
        dartBoardAction.doPressButton();
        verify(dartGameNotification, never()).onGameStarted(game);
    }

    @Test
    void testBust() {
        when(scoreBoard.isBust(player1)).thenReturn(true);

        game.initGame(dartBoardAction, scoreBoard, dartGameNotification);
        game.addPlayer(player1);
        dartBoardAction.doPressButton();
        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);

        verify(dartGameNotification).onPlayerBust(game, player1);
        verify(dartGameNotification).onGameFinished(game);
    }

    private void throw3dartsAndPressButton(Dart first, Dart second, Dart third) {
        dartBoardAction.doThrowDart(first);
        dartBoardAction.doThrowDart(second);
        dartBoardAction.doThrowDart(third);
        dartBoardAction.doPressButton();
    }
}
