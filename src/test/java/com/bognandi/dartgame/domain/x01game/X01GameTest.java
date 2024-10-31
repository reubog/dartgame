package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.game.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bognandi.dartgame.domain.game.Dart.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class X01GameTest {

    private DartGame game = new X01Game(301);

    @Mock
    private Notifyer notifyer;

    private DefaultDartBoardAction dartBoardAction = new DefaultDartBoardAction();

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private Player player3;

    @Test
    void testGamePlay() {
        game.initGame(dartBoardAction, notifyer);
        game.addPlayer(player1);
        game.addPlayer(player2);

        verify(notifyer).onPlayerAdded(game, player1);
        verify(notifyer).onPlayerAdded(game, player2);

        dartBoardAction.doPressButton();

        verify(notifyer).onGameStarted(game);

        verify(notifyer).onRoundStarted(game, 1);
        verify(notifyer).onPlayerTurn(game, 1, player1);
        dartBoardAction.doThrowDart(ONE);
        verify(notifyer).onDartThrown(game, ONE);
        dartBoardAction.doThrowDart(TWO);
        verify(notifyer).onDartThrown(game, TWO);
        dartBoardAction.doThrowDart(THREE);
        verify(notifyer).onDartThrown(game, THREE);
        verify(notifyer).onRemoveDarts(game, 1, player1);
        dartBoardAction.doThrowDart(FOUR);
        verify(notifyer, never()).onDartThrown(game, FOUR);
        dartBoardAction.doPressButton();
        verify(notifyer).onPlayerTurn(game, 1, player2);
        dartBoardAction.doThrowDart(FIVE);
        verify(notifyer).onDartThrown(game, FIVE);
        dartBoardAction.doPressButton();
        verify(notifyer).onRemoveDarts(game, 1, player2);
        dartBoardAction.doThrowDart(SIX);
        verify(notifyer, never()).onDartThrown(game, SIX);
        dartBoardAction.doPressButton();

        verify(notifyer).onRoundStarted(game, 2);
        verify(notifyer).onPlayerTurn(game, 2, player1);
        dartBoardAction.doPressButton();
        verify(notifyer).onPlayerTurn(game, 2, player2);
        dartBoardAction.doPressButton();


    }

    @Test
    void testWinAndGameFinished() {
        game.initGame(dartBoardAction, notifyer);
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
        dartBoardAction.doThrowDart(ONE);
        verify(notifyer).onPlayerWon(game, player1);
        verify(notifyer).onRemoveDarts(game, 3, player1);
        dartBoardAction.doPressButton();

        // player 2
        dartBoardAction.doThrowDart(ELEVEN);
        verify(notifyer).onPlayerWon(game, player2);

        verify(notifyer).onGameFinished(game);
    }

    @Test
    void testSinglePlay() {
        game.initGame(dartBoardAction, notifyer);
        game.addPlayer(player1);
        dartBoardAction.doPressButton();

        throw3dartsAndPressButton(DOUBLE_BULLSEYE, DOUBLE_BULLSEYE, DOUBLE_BULLSEYE);
        throw3dartsAndPressButton(DOUBLE_BULLSEYE, DOUBLE_BULLSEYE, DOUBLE_BULLSEYE);

        dartBoardAction.doThrowDart(ONE);
        verify(notifyer).onPlayerWon(game, player1);
        verify(notifyer).onGameFinished(game);
    }



    @Test
    void testStartGameWithoutPlayserShouldNotStart() {
        game.initGame(dartBoardAction, notifyer);
        dartBoardAction.doPressButton();
        verify(notifyer, never()).onGameStarted(game);
    }

    @Test
    @Disabled
    void testBust() {
        game.initGame(dartBoardAction, notifyer);
        game.addPlayer(player1);
        dartBoardAction.doPressButton();

        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);
        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);
        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);

        dartBoardAction.doPressButton();

        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);
        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);
        dartBoardAction.doThrowDart(DOUBLE_BULLSEYE);

        dartBoardAction.doPressButton();

        dartBoardAction.doThrowDart(ONE);

        verify(notifyer).onPlayerWon(game, player1);
        verify(notifyer).onGameFinished(game);
    }

    private void throw3dartsAndPressButton(Dart first, Dart second, Dart third) {
        dartBoardAction.doThrowDart(first);
        dartBoardAction.doThrowDart(second);
        dartBoardAction.doThrowDart(third);
        dartBoardAction.doPressButton();
    }
}
