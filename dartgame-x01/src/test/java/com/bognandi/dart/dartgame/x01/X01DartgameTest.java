package com.bognandi.dart.dartgame.x01;

import com.bognandi.dart.core.dartgame.Dart;
import com.bognandi.dart.core.dartgame.Dartgame;
import com.bognandi.dart.core.dartgame.DartgameEventListener;
import com.bognandi.dart.core.dartgame.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class X01DartgameTest {


    @Mock
    private DartgameEventListener dartGameEventListener;

    private ImplDartboard dartBoard = new ImplDartboard();

    @Mock
    private X01ScoreBoard scoreBoard;

    private X01Dartgame game;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private Player player3;

    @BeforeEach
    void setUp() {
        Mockito.when(scoreBoard.getMinimumNumberOfPlayers()).thenReturn(2);

        game = new X01Dartgame(scoreBoard);
        game.initGameWaitForPlayers();
        game.addEventListener(dartGameEventListener);
        game.attachDartboard(dartBoard);
    }

    @Test
    void testGamePlay() {
        game.setPlayers(List.of(player1, player2));

//        Mockito.verify(dartGameEventListener).onPlayerAdded(game, player1);
//        Mockito.verify(dartGameEventListener).onPlayerAdded(game, player2);

        dartBoard.doPressButton();

        Mockito.verify(dartGameEventListener).onGamePlayStarted(game);

        Mockito.verify(dartGameEventListener).onRoundStarted(game, 1);
        Mockito.verify(dartGameEventListener).onPlayerTurn(game, 1, player1);
        dartBoard.doThrowDart(Dart.ONE);
        Mockito.verify(dartGameEventListener).onDartThrown(game, Dart.ONE);
        dartBoard.doThrowDart(Dart.TWO);
        Mockito.verify(dartGameEventListener).onDartThrown(game, Dart.TWO);
        dartBoard.doThrowDart(Dart.THREE);
        Mockito.verify(dartGameEventListener).onDartThrown(game, Dart.THREE);
        Mockito.verify(dartGameEventListener).onRemoveDarts(game, 1, player1);
        dartBoard.doThrowDart(Dart.FOUR);
        Mockito.verify(dartGameEventListener, Mockito.never()).onDartThrown(game, Dart.FOUR);
        dartBoard.doPressButton();
        Mockito.verify(dartGameEventListener).onPlayerTurn(game, 1, player2);
        dartBoard.doThrowDart(Dart.FIVE);
        Mockito.verify(dartGameEventListener).onDartThrown(game, Dart.FIVE);
        dartBoard.doPressButton();
        Mockito.verify(dartGameEventListener).onRemoveDarts(game, 1, player2);
        dartBoard.doThrowDart(Dart.SIX);
        Mockito.verify(dartGameEventListener, Mockito.never()).onDartThrown(game, Dart.SIX);
        dartBoard.doPressButton();

        Mockito.verify(dartGameEventListener).onRoundStarted(game, 2);
        Mockito.verify(dartGameEventListener).onPlayerTurn(game, 2, player1);
        dartBoard.doPressButton();
        Mockito.verify(dartGameEventListener).onPlayerTurn(game, 2, player2);
        dartBoard.doPressButton();


    }

    @Test
    void testWinAndGameFinished() {
        game.setPlayers(List.of(player1, player2, player3));
        dartBoard.doPressButton();

        // player 1
        throw3dartsAndPressButton(Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE);

        // player 2
        throw3dartsAndPressButton(Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE);

        // player 3
        throw3dartsAndPressButton(Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE);


        // player 1
        throw3dartsAndPressButton(Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE);

        // player 2
        throw3dartsAndPressButton(Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_BULLSEYE, Dart.DOUBLE_TWENTY);

        // player 3
        throw3dartsAndPressButton(Dart.THREE, Dart.THREE, Dart.THREE);

        // player 1
        Mockito.when(scoreBoard.isWinner(player1)).thenReturn(true);
        dartBoard.doThrowDart(Dart.ONE);
        Mockito.verify(dartGameEventListener).onPlayerWon(game, player1);
        dartBoard.doPressButton();
//
//        // player 2
//        Mockito.verify(dartGameEventListener).onPlayerTurn(game, 3, player2);
//        Mockito.when(scoreBoard.isWinner(player2)).thenReturn(true);
//        dartBoard.doThrowDart(Dart.ELEVEN);
//        Mockito.verify(dartGameEventListener).onPlayerWon(game, player2);

//        Mockito.verify(dartGameEventListener).onGameFinished(game);
    }

    @Test
    void testTwoPlayersWhenPlayerWinGameShouldFinish() {
        game.setPlayers(List.of(player1, player2));
        Mockito.when(scoreBoard.isWinner(player1)).thenReturn(true);

        dartBoard.doPressButton();
        dartBoard.doThrowDart(Dart.ONE);

        Mockito.verify(dartGameEventListener).onPlayerWon(game, player1);
        Mockito.verify(dartGameEventListener).onGameFinished(game);
    }


    @Test
    void testStartGameWithoutPlayerShouldNotInitWaitForPlayers() {
        game.setPlayers(List.of(player1));
        dartBoard.doPressButton();
        Mockito.verify(dartGameEventListener, Mockito.never()).onGamePlayStarted(game);
    }

    @Test
    void testBust() {
        game.setPlayers(List.of(player1, player2));
        Mockito.when(scoreBoard.isBust(player1)).thenReturn(true);

        dartBoard.doPressButton();
        dartBoard.doThrowDart(Dart.DOUBLE_BULLSEYE);

        Mockito.verify(dartGameEventListener).onPlayerBust(game, player1);
    }

    @Test
    void testWinnerShouldNotPlayAnymore() {
        GameEvents gameEvents = new GameEvents();
        game.addEventListener(gameEvents);
        game.setPlayers(List.of(player1, player2, player3));
        Mockito.when(scoreBoard.isWinner(player1)).thenReturn(true);

        dartBoard.doPressButton(); // start round player1
        dartBoard.doThrowDart(Dart.ONE);
        //verify(dartGameEventListener).onPlayerWon(game, player1);

        dartBoard.doPressButton(); // player 2
        Assertions.assertEquals(player2, gameEvents.getCurrentPlayer());
        dartBoard.doPressButton(); // player 3
        Assertions.assertEquals(player3, gameEvents.getCurrentPlayer());
        dartBoard.doPressButton(); // player 2
        Assertions.assertEquals(player2, gameEvents.getCurrentPlayer());
        dartBoard.doPressButton(); // player 3
        Assertions.assertEquals(player3, gameEvents.getCurrentPlayer());
    }

    private void throw3dartsAndPressButton(Dart first, Dart second, Dart third) {
        dartBoard.doThrowDart(first);
        dartBoard.doThrowDart(second);
        dartBoard.doThrowDart(third);
        dartBoard.doPressButton();
    }

    class GameEvents implements DartgameEventListener {

        private Player currentPlayer;

        public void onWaitingForPlayers(Dartgame dartGame) {

        }

        @Override
        public void onGamePlayStarted(Dartgame dartGame) {

        }

        @Override
        public void onGameFinished(Dartgame dartGame) {

        }

        public void onPlayerAdded(Dartgame dartGame, Player player) {

        }

        @Override
        public void onRoundStarted(Dartgame dartGame, int roundNumber) {

        }

        @Override
        public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
            currentPlayer = player;
        }

        @Override
        public void onDartThrown(Dartgame dartGame, Dart dart) {

        }

        @Override
        public void onRemoveDarts(Dartgame dartGame, int round, Player player) {

        }

        @Override
        public void onPlayerWon(Dartgame dartGame, Player player) {

        }

        @Override
        public void onPlayerBust(Dartgame dartGame, Player player) {

        }

        @Override
        public void onPlayerLost(Dartgame dartGame, Player player) {

        }

        public Player getCurrentPlayer() {
            return currentPlayer;
        }
    }
}
