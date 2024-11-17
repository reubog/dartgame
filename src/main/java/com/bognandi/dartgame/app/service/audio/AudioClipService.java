package com.bognandi.dartgame.app.service.audio;

import javafx.scene.media.AudioClip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AudioClipService {

    private static final Logger LOG = LoggerFactory.getLogger(AudioClipService.class);

    private Map<SoundClip, AudioClip> audioClipMap = new LinkedHashMap<>();

    public enum SoundClip {
        SINGLE("single.wav"),
        DOUBLE("double.wav"),
        TRIPLE("triple.wav"),
        OUTER_BULLSEYE("outer_bull.wav"),
        INNER_BULLSEYE("inner_bull.wav"),

        GAME_OVER("Game-over-yeah.wav"),
        APPLAUSE("Small-crowd-clapping-sound-effect.wav"),
        LOOSE("Sad-trumpet-sound-effect.wav"),
        BELL("Taco-bell-sound.wav"),
        ;

        String resourceName;

        SoundClip(String resourceName) {
            this.resourceName = resourceName;
        }

        String getResourceName() {
            return resourceName;
        }
    }

    public AudioClipService() throws IOException {
        LOG.debug("AudioClipService created");

        Arrays.stream(new PathMatchingResourcePatternResolver().getResources("classpath*:/audio/*"))
                .map(this::resource2url)
                .forEach(this::addAudioClip);

    }

    public void play(SoundClip clip) {
        audioClipMap.get(clip).play();
    }
    public void stop(SoundClip clip) {
        audioClipMap.get(clip).stop();
    }

    private void addAudioClip(String url) {
        Arrays.stream(SoundClip.values())
                .filter(value -> url.contains(value.getResourceName()))
                .findFirst()
                .ifPresentOrElse(value -> {
                            LOG.debug("Mapping clip {} with audio {}", value, url);
                            audioClipMap.put(value, new AudioClip(url));
                        },
                        () -> LOG.debug("Skipping audio clip: {}", url)
                );
    }

    private String resource2url(Resource resource) {
        try {
            return resource.getURL().toExternalForm();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
