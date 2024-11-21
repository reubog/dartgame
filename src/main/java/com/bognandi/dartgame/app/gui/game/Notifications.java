package com.bognandi.dartgame.app.gui.game;

import javafx.application.Platform;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;


public class Notifications {

    public static final String WAITING_FOR_PLAYERS = "waiting-for-players";
    public static final String PLAYER_ADDED = "player-added";
    public static final String START_GAME = "start-game";
    public static final String ROUND_STARTED = "round-started";
    public static final String PLAYER_TURN = "player-turn";
    public static final String DART_THROWN = "dart-thrown";

    private final Map<String, List<SubscriberObject>> subscribers = new LinkedHashMap<>();

    private static Notifications instance = new Notifications();

    public static Notifications createInstance() {
        return instance;
    }

    private Notifications() {}

    public void publish(String event) {

        Platform.runLater(() -> {
            List<SubscriberObject> subscriberList = instance.subscribers.get(event);

            if (subscriberList != null) {

                subscriberList.forEach(
                        subscriberObject -> subscriberObject.getEventConsumer().accept(event)
                );

                // event ends after last subscriber gets callback
            }
        });
    }

    public void subscribe(String event, Object subscriber, Consumer<String> eventConsumer) {

        if (!instance.subscribers.containsKey(event)) {
            List<SubscriberObject> slist = new ArrayList<>();
            instance.subscribers.put(event, slist);
        }

        List<SubscriberObject> subscriberList = instance.subscribers.get(event);

        subscriberList.add(new SubscriberObject(subscriber, eventConsumer));
    }

    public void unsubscribe(String event, Object subscriber) {

        List<SubscriberObject> subscriberList = instance.subscribers.get(event);

        if (subscriberList == null) {
            subscriberList.remove(subscriber);
        }
    }

    static class SubscriberObject {

        private final Object subscriber;
        private final Consumer<String> eventConsumer;

        public SubscriberObject(Object subscriber,
                                Consumer<String> eventConsumer) {
            this.subscriber = subscriber;
            this.eventConsumer = eventConsumer;
        }

        public Object getSubscriber() {
            return subscriber;
        }

        public Consumer<String> getEventConsumer() {
            return eventConsumer;
        }

        @Override
        public int hashCode() {
            return subscriber.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return subscriber.equals(obj);
        }
    }
}