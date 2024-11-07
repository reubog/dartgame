package com.bognandi.dartgame.app.service;

import com.bognandi.dartgame.app.service.dartboard.DartboardService;
import com.bognandi.dartgame.app.service.speech.SpeechService;
import com.bognandi.dartgame.domain.dartgame.Dart;
import com.bognandi.dartgame.domain.dartgame.DartBoardEventListener;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(GameService.class);
    private SpeechService speechService;
    private DartboardService dartboardService;
    private DartBoardEventListener dartBoardEventListener;

    public GameService(@Autowired SpeechService speechService, @Autowired DartboardService dartboardService, @Value("${dartboard.name}") String dartboardName) {
        this.speechService = speechService;
        this.dartboardService = dartboardService;
        this.dartBoardEventListener = new DartboardListener();
        LOG.info("Constructing service");

        dartboardService.findDartboard(dartboardName, dartboard -> dartboard.addEventListener(dartBoardEventListener));
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Destroying!");
        dartboardService.destroy();
    }

    private class DartboardListener implements DartBoardEventListener {
        @Override
        public void onDartThrown(Dart dart) {
            speechService.doSpeak(dart.name().replace("_", " "));
        }

        @Override
        public void onButton() {
            speechService.doSpeak("Button pressed");
        }
    }

}
