package com.bognandi.dartgame.app.service.audio;

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

    public String getResourceName() {
        return resourceName;
    }
}
