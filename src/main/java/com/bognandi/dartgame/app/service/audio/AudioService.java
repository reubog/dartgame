package com.bognandi.dartgame.app.service.audio;

import jakarta.annotation.PreDestroy;
import javafx.scene.media.AudioClip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AudioService {

    private static final Logger LOG = LoggerFactory.getLogger(AudioService.class);

    private Map<SoundClip,Clip> audioClipMap = new LinkedHashMap<>();
    private String audioFilesLocation;

    public AudioService(@Value("${audio.path}") String audioFilesLocation) {
        LOG.debug("Audio service is starting");
        this.audioFilesLocation = audioFilesLocation;


//        URL url = this.getClass().getResource("/audio/double.wav");
//
//        AudioClip clip = new AudioClip(url.toExternalForm());
//


//        Arrays.stream(SoundClip.values())
//                .forEach(enumVal -> {
//                    String resource = Path.of("/audio", enumVal.getResourceName()).toString();
//                    try {
//
//                        InputStream inputStream = this.getClass().getResourceAsStream(resource);
////                    AudioClip clip = new AudioClip(resource);
//
//                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
//                        Clip clip = AudioSystem.getClip();
//                        clip.open(audioInputStream);
//                        audioClipMap.put(enumVal, clip);
//
//                    } catch (UnsupportedAudioFileException e) {
//                        throw new RuntimeException("Cannot find load resource: " + resource, e);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Cannot find load resource: " + resource, e);
//                    } catch (LineUnavailableException e) {
//                        throw new RuntimeException("Cannot find load resource: " + resource, e);
//                    }
//                });

//        try {
//            init();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

//    private void init() throws IOException {
//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(audioFilesLocation);
//
//        Arrays.stream(resources)
//                .filter(Resource::isFile)
//                .filter(Resource::isReadable)
//                .forEach(resource -> {
//                    FileSystemResource fr = (FileSystemResource) resource;
//                    String path = fr.getPath();
//                    String[] pathElements = path.split("/");
//                    String name = pathElements[pathElements.length - 1];
//                    String resourceName = audioFilesLocation.replace("*", name);
//                    String url = AudioService.class.getResource(resourceName).toString();
//                    audioClipMap.put(
//                            name,
//                            new AudioClip(url));
//                });
//
//        LOG.debug("{} audioclips loaded", audioClipMap.size());
//    }

    public Collection<SoundClip> getAudioClipNames() {
        return audioClipMap.keySet();
    }

    public void playAudioClip(SoundClip soundClip) {
        Clip clip = audioClipMap.get(soundClip);
        clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    @PreDestroy
    public void shutdown() {
        LOG.debug("Shutting down audio clips");
        audioClipMap.values().forEach(audioClip -> audioClip.close());
    }

    public static void main(String args) {
        AudioService service = new AudioService("/audio");

        service.playAudioClip(SoundClip.OUTER_BULLSEYE);

//        Thread.sleep(5*1000);
        service.shutdown();
    }
}
