package com.bognandi.dartgame.domain.x01game;

import com.bognandi.dartgame.domain.dartgame.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.bognandi.dartgame.domain.dartgame.Dart.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class X01DartGameTest {

    private X01DartGame game = new X01DartGame();

    @Mock
    private DartGameEventListener dartGameEventListener;

    private DefaultDartboard dartBoard = new DefaultDartboard();

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
        when(scoreBoard.getMinimumNumberOfPlayers()).thenReturn(2);

        game.startGame(scoreBoard);
        game.addEventListener(dartGameEventListener);
        dartBoard.addEventListener(game);

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

    private void throw3dartsAndPressButton(Dart first, Dart second, Dart third) {
        dartBoard.doThrowDart(first);
        dartBoard.doThrowDart(second);
        dartBoard.doThrowDart(third);
        dartBoard.doPressButton();
    }

    class TestScoreBoard implements ScoreBoard, DartGameEventListener {
        private List<Player> players = new ArrayList<>();

        @Override
        public PlayerScore getPlayerScore(Player player) {
            return null;
        }

        @Override
        public int getMinimumNumberOfPlayers() {
            return 0;
        }


        @Override
        public boolean isWinner(Player player) {
            return false;
        }

        @Override
        public boolean isBust(Player player) {
            return false;
        }

        @Override
        public void onGameStarting(DartGame dartGame) {

        }

        @Override
        public void onGameStarted(DartGame dartGame) {

        }

        @Override
        public void onGameFinished(DartGame dartGame) {

        }

        @Override
        public void onPlayerAdded(DartGame dartGame, Player player) {

        }

        @Override
        public void onRoundStarted(DartGame dartGame, int roundNumber) {

        }

        @Override
        public void onPlayerTurn(DartGame dartGame, int roundNumber, Player player) {

        }

        @Override
        public void onDartThrown(DartGame dartGame, Dart dart) {

        }

        @Override
        public void onRemoveDarts(DartGame dartGame, int round, Player player) {

        }

        @Override
        public void onPlayerWon(DartGame dartGame, Player player) {

        }

        @Override
        public void onPlayerBust(DartGame dartGame, Player player) {

        }
    }
}
