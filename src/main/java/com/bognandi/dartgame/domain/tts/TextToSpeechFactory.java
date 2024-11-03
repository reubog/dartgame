package com.bognandi.dartgame.domain.tts;

import com.sun.speech.freetts.VoiceManager;

import javax.speech.AudioListener;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

public class TextToSpeechFactory {

    public static void main(String[] args) {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
//            synthesizer.speakPlainText("1 2 3 testing testing", null);
//
//            synthesizer.speakPlainText("1 2 3 testing testing", null);

            synthesizer.speakPlainText("alfred and alvina go to bed now!", null);

            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);

            // Deallocate the Synthesizer.
            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
