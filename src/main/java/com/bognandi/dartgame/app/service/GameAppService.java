package com.bognandi.dartgame.app.service;

import com.bognandi.dartgame.app.service.audio.AudioService;
import com.bognandi.dartgame.app.service.audio.SoundClip;
import com.bognandi.dartgame.app.service.dartboard.DartboardService;
import com.bognandi.dartgame.app.service.speech.SpeechService;
import com.bognandi.dartgame.domain.dartgame.*;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
public class GameAppService {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(GameAppService.class);

    private GameAppState gameAppState;
    private SpeechService speechService;
    private AudioService audioService;
    private DartboardService dartboardService;
    private DartgameFactory dartgameFactory;
    private ProxyDartboardListener proxyDartboardListener;

    public GameAppService(@Autowired SpeechService speechService, @Autowired AudioService audioService, @Autowired DartboardService dartboardService, @Value("${dartboard.name}") String dartboardName, @Autowired DartgameFactory dartgameFactory) {
        this.speechService = speechService;
        this.audioService = audioService;
        this.dartboardService = dartboardService;
        this.dartgameFactory = dartgameFactory;
        this.proxyDartboardListener = new ProxyDartboardListener();
        LOG.info("Constructing service");

//        dartboardService.findDartboard(dartboardName, dartboard -> {
//            LOG.info("Dartboard listener attached");
//            dartboard.addEventListener(proxyDartboardListener);
//        });
        gameAppState = GameAppState.APP_STARTED;
    }

    public List<DartgameDescriptor> getAvailableDartgames() {
        gameAppState = GameAppState.SELECT_GAME;
        return dartgameFactory.getDartgames();
    }

    public void playGame(String name, int numberOfPlayers) {
        //Platform.runLater(() -> {
        Dartgame dartGame = dartgameFactory.createDartgame(name);
        dartGame.addEventListener(new GameEventListener());

        proxyDartboardListener.addListener((DartBoardEventListener) dartGame);

        dartGame.startGame();

        IntStream.range(0,numberOfPlayers).forEach(val -> dartGame.addPlayer(new GamePlayer("Player " + val)));

        //proxyDartboardListener.removeListener((DartBoardEventListener) dartGame);
        //  });
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Destroying!");
    }

    private class ProxyDartboardListener implements DartBoardEventListener {

        private List<DartBoardEventListener> listeners = new ArrayList<>();

        public void addListener(DartBoardEventListener listener) {
            listeners.add(listener);
        }

        public void removeListener(DartBoardEventListener listener) {
            listeners.remove(listener);
        }

        @Override
        public void onDartThrown(Dart dart) {
            listeners.forEach(listener -> listener.onDartThrown(dart));
        }

        @Override
        public void onButton() {
            listeners.forEach(listener -> listener.onButton());
        }

    }

    private class GamePlayer implements Player {
        private String name;

        public GamePlayer(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private class GameEventListener implements DartgameEventListener {

        private ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        private Player currentPlayer;
        private int startRoundScore;

        @Override
        public void onGameStarting(Dartgame dartGame) {
            speak("waiting for players and button press");
        }

        @Override
        public void onGameStarted(Dartgame dartGame) {
            speak("game started");
            audioService.playAudioClip(SoundClip.BELL);
        }

        @Override
        public void onGameFinished(Dartgame dartGame) {
            speak("game finished");
            audioService.playAudioClip(SoundClip.GAME_OVER);
        }

        @Override
        public void onPlayerAdded(Dartgame dartGame, Player player) {
            speak("welcome to the game " + player.getName());
        }

        @Override
        public void onRoundStarted(Dartgame dartGame, int roundNumber) {
            speak("ready for round " + roundNumber);

        }

        @Override
        public void onPlayerTurn(Dartgame dartGame, int roundNumber, Player player) {
            currentPlayer = player;
            startRoundScore = dartGame.getPlayerScore(player).getScore();
            speak("next player. please go ahead " + player.getName() + ". your current score is " + startRoundScore);
        }

        @Override
        public void onDartThrown(Dartgame dartGame, Dart dart) {
            Optional.ofNullable(mapDartToSoundclip(dart)).ifPresent((soundClip -> executorService.submit(() -> audioService.playAudioClip(soundClip.get()))));

            int score = dartGame.getPlayerScore(currentPlayer).getScore();

            StringBuilder sb = new StringBuilder();
            sb.append(dart.name().replace("_", " "));

            if (score > 0 && score < 50) {
                sb.append("be careful now. your score is " + score);
            }

            speak(sb.toString());
        }

        @Override
        public void onRemoveDarts(Dartgame dartGame, int round, Player player) {
            speak("remove darts and press button");
        }

        @Override
        public void onPlayerWon(Dartgame dartGame, Player player) {
            speak("Contgratulations! " + player.getName() + " won the game");
            audioService.playAudioClip(SoundClip.APPLAUSE);
        }

        @Override
        public void onPlayerBust(Dartgame dartGame, Player player) {
            speak("Bust! " + player.getName() + " is bust. remove darts and press button");
            audioService.playAudioClip(SoundClip.LOOSE);
        }

        @Override
        public void onPlayerLost(Dartgame dartGame, Player player) {
            speak("Sorry! " + player.getName() + " lost the game");
        }

        void speak(String speech) {
            executorService.submit(() -> speechService.doSpeak(speech));
        }

        Optional<SoundClip> mapDartToSoundclip(Dart dart) {
            switch (dart) {
                case ONE:
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                case SEVEN:
                case EIGHT:
                case NINE:
                case TEN:
                case ELEVEN:
                case TWELVE:
                case THIRTEEN:
                case FOURTEEN:
                case FIFTEEN:
                case SIXTEEN:
                case SEVENTEEN:
                case EIGHTEEN:
                case NINETEEN:
                case TWENTY:
                    return Optional.of(SoundClip.SINGLE);

                case BULLSEYE:
                    return Optional.of(SoundClip.OUTER_BULLSEYE);

                case DOUBLE_ONE:
                case DOUBLE_TWO:
                case DOUBLE_THREE:
                case DOUBLE_FOUR:
                case DOUBLE_FIVE:
                case DOUBLE_SIX:
                case DOUBLE_SEVEN:
                case DOUBLE_EIGHT:
                case DOUBLE_NINE:
                case DOUBLE_TEN:
                case DOUBLE_ELEVEN:
                case DOUBLE_TWELVE:
                case DOUBLE_THIRTEEN:
                case DOUBLE_FOURTEEN:
                case DOUBLE_FIFTEEN:
                case DOUBLE_SIXTEEN:
                case DOUBLE_SEVENTEEN:
                case DOUBLE_EIGHTEEN:
                case DOUBLE_NINETEEN:
                case DOUBLE_TWENTY:
                    return Optional.of(SoundClip.DOUBLE);

                case DOUBLE_BULLSEYE:
                    return Optional.of(SoundClip.INNER_BULLSEYE);

                case TRIPLE_ONE:
                case TRIPLE_TWO:
                case TRIPLE_THREE:
                case TRIPLE_FOUR:
                case TRIPLE_FIVE:
                case TRIPLE_SIX:
                case TRIPLE_SEVEN:
                case TRIPLE_EIGHT:
                case TRIPLE_NINE:
                case TRIPLE_TEN:
                case TRIPLE_ELEVEN:
                case TRIPLE_TWELVE:
                case TRIPLE_THIRTEEN:
                case TRIPLE_FOURTEEN:
                case TRIPLE_FIFTEEN:
                case TRIPLE_SIXTEEN:
                case TRIPLE_SEVENTEEN:
                case TRIPLE_EIGHTEEN:
                case TRIPLE_NINETEEN:
                case TRIPLE_TWENTY:
                    return Optional.of(SoundClip.TRIPLE);

                default:
                    return Optional.empty();
            }
        }
    }

}
