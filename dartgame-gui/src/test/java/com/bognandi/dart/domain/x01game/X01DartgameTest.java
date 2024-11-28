package com.bognandi.dart.domain.x01game;

import com.bognandi.dart.core.dartgame.*;
import com.bognandi.dart.dartgame.x01game.x01game.X01Dartgame;
import com.bognandi.dart.dartgame.x01game.x01game.X01ScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bognandi.dart.core.dartgame.Dart.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        when(scoreBoard.getMinimumNumberOfPlayers()).thenReturn(2);

        game = new X01Dartgame(scoreBoard);
        game.startGame();
        game.addEventListener(dartGameEventListener);
        game.attachDartboard(dartBoard);
    }

    @Test
    void testGamePlay() {
        game.addPlayer(player1);
        game.addPlayer(player2);

        verify(dartGameEventListener).onPlayerAdded(game, player1);
        verify(dartGameEventListener).onPlayerAdded(game, player2);

        dartBoard.doPressButton();

        verify(dartGameEventListener).onGameStarted(game);

        verify(dartGameEventListener).onRoundStarted(game, 1);
        verify(dartGameEventListener).onPlayerTurn(game, 1, player1);
        dartBoard.doThrowDart(ONE);
        verify(dartGameEventListener).onDartThrown(game, ONE);
        dartBoard.doThrowDart(TWO);
        verify(dartGameEventListener).onDartThrown(game, TWO);
        dartBoard.doThrowDart(THREE);
        verify(dartGameEventListener).onDartThrown(game, THREE);
        verify(dartGameEventListener).onRemoveDarts(game, 1, player1);
        dartBoard.doThrowDart(FOUR);
        verify(dartGameEventListener, never()).onDartThrown(game, FOUR);
        dartBoard.doPressButton();
        verify(dartGameEventListener).onPlayerTurn(game, 1, player2);
        dartBoard.doThrowDart(FIVE);
        verify(dartGameEventListener).onDartThrown(game, FIVE);
        dartBoard.doPressButton();
        verify(dartGameEventListener).onRemoveDarts(game, 1, player2);
        dartBoard.doThrowDart(SIX);
        verify(dartGameEventListener, never()).onDartThrown(game, SIX);
        dartBoard.doPressButton();

        verify(dartGameEventListener).onRoundStarted(game, 2);
        verify(dartGameEventListener).onPlayerTurn(game, 2, player1);
        dartBoard.doPressButton();
        verify(dartGameEventListener).onPlayerTurn(game, 2, player2);
        dartBoard.doPressButton();


    }

    @Test
    void testWinAndGameFinished() {
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        dartBoard.doPressButton();

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
        dartBoard.doThrowDart(ONE);
        verify(dartGameEventListener).onPlayerWon(game, player1);
        dartBoard.doPressButton();

        // player 2
        verify(dartGameEventListener).onPlayerTurn(game, 3, player2);
        when(scoreBoard.isWinner(player2)).thenReturn(true);
        dartBoard.doThrowDart(ELEVEN);
        verify(dartGameEventListener).onPlayerWon(game, player2);

        verify(dartGameEventListener).onGameFinished(game);
    }

    @Test
    void testTwoPlayersWhenPlayerWinGameShouldFinish() {
        game.addPlayer(player1);
        game.addPlayer(player2);
        when(scoreBoard.isWinner(player1)).thenReturn(true);

        dartBoard.doPressButton();
        dartBoard.doThrowDart(ONE);

        verify(dartGameEventListener).onPlayerWon(game, player1);
        verify(dartGameEventListener).onGameFinished(game);
    }



    @Test
    void testStartGameWithoutPlayerShouldNotStart() {
        game.addPlayer(player1);
        dartBoard.doPressButton();
        verify(dartGameEventListener, never()).onGameStarted(game);
    }

    @Test
    void testBust() {
        game.addPlayer(player1);
        game.addPlayer(player2);
        when(scoreBoard.isBust(player1)).thenReturn(true);

        dartBoard.doPressButton();
        dartBoard.doThrowDart(DOUBLE_BULLSEYE);

        verify(dartGameEventListener).onPlayerBust(game, player1);
    }

    @Test
    void testWinnerShouldNotPlayAnymore() {
        GameEvents gameEvents = new GameEvents();
        game.addEventListener(gameEvents);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        when(scoreBoard.isWinner(player1)).thenReturn(true);

        dartBoard.doPressButton(); // start round player1
        dartBoard.doThrowDart(ONE);
        //verify(dartGameEventListener).onPlayerWon(game, player1);

        dartBoard.doPressButton(); // player 2
        assertEquals(player2, gameEvents.getCurrentPlayer());
        dartBoard.doPressButton(); // player 3
        assertEquals(player3, gameEvents.getCurrentPlayer());
        dartBoard.doPressButton(); // player 2
        assertEquals(player2, gameEvents.getCurrentPlayer());
        dartBoard.doPressButton(); // player 3
        assertEquals(player3, gameEvents.getCurrentPlayer());
    }

    private void throw3dartsAndPressButton(Dart first, Dart second, Dart third) {
        dartBoard.doThrowDart(first);
        dartBoard.doThrowDart(second);
        dartBoard.doThrowDart(third);
        dartBoard.doPressButton();
    }

    class GameEvents implements DartgameEventListener {

        private Player currentPlayer;

        @Override
        public void onGameStarting(Dartgame dartGame) {

        }

        @Override
        public void onGameStarted(Dartgame dartGame) {

        }

        @Override
        public void onGameFinished(Dartgame dartGame) {

        }

        @Override
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
