package com.bognandi.dartgame.app.service.speech;

import com.bognandi.dartgame.domain.dartgame.Dart;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.Locale;

@Service
public class SpeechService {

    private static final Logger LOG = LoggerFactory.getLogger(SpeechService.class);

    private SynthesizerModeDesc desc;
    private Synthesizer synthesizer;

    public SpeechService(@Value("${speech.voice.name}") String voiceName) {
        LOG.info("Service starting");
        init(voiceName);
    }

    public void doSpeak(String text) {
        LOG.info("Speaking '{}'", text);
        synthesizer.speakPlainText(text, null);
        try {
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted while waiting for speech engine", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("Service shutting down");
        try {
            synthesizer.deallocate();
        } catch (EngineException e) {
            throw new SpeechServiceException("Failed to deallocate synthizer", e);
        }
    }

    private void init(String voiceName) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        desc = new SynthesizerModeDesc(Locale.US);

        try {
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(desc);
            synthesizer.allocate();
            synthesizer.resume();
        } catch (Exception e) {
            throw new SpeechServiceException("Unable to initialize speech service", e);
        }

        SynthesizerModeDesc smd = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
        Voice[] voices = smd.getVoices();
        Arrays.stream(voices)
                .filter(voice -> voice.getName().equals(voiceName))
                .findFirst()
                .ifPresent(voice -> {
                    try {
                        synthesizer.getSynthesizerProperties().setVoice(voice);
                    } catch (PropertyVetoException e) {
                        throw new SpeechServiceException("Unable to set voice " + voiceName, e);
                    }
                });

        LOG.info("Service initialized");
    }

    public static void main(String[] args) throws Exception {
        SpeechService service = new SpeechService("kevin16");
        //service.doSpeak("Hello world!");

        Arrays.stream(Dart.values())
                .map(value -> value.name().replace("_", " "))
                .forEach(service::doSpeak);

        service.shutdown();
    }
}
