package com.bognandi.dartgame.app.gui.game;

import javafx.application.Platform;

import java.util.*;
import java.util.function.Consumer;

public class Notifications {

    private final Map<UUID, List<SubscriberObject>> subscribers = new LinkedHashMap<>();

    private static Notifications instance = new Notifications();

    public static Notifications createInstance() {
        return instance;
    }

    private Notifications() {}

    public void publish(UUID event) {

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

    public void subscribe(UUID event, Object subscriber, Consumer<UUID> eventConsumer) {

        if (!instance.subscribers.containsKey(event)) {
            List<SubscriberObject> slist = new ArrayList<>();
            instance.subscribers.put(event, slist);
        }

        List<SubscriberObject> subscriberList = instance.subscribers.get(event);

        subscriberList.add(new SubscriberObject(subscriber, eventConsumer));
    }

    public void unsubscribe(UUID event, Object subscriber) {

        List<SubscriberObject> subscriberList = instance.subscribers.get(event);

        if (subscriberList == null) {
            subscriberList.remove(subscriber);
        }
    }

    static class SubscriberObject {

        private final Object subscriber;
        private final Consumer<UUID> eventConsumer;

        public SubscriberObject(Object subscriber,
                                Consumer<UUID> eventConsumer) {
            this.subscriber = subscriber;
            this.eventConsumer = eventConsumer;
        }

        public Object getSubscriber() {
            return subscriber;
        }

        public Consumer<UUID> getEventConsumer() {
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