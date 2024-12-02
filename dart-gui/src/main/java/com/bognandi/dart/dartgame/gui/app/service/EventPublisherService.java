package com.bognandi.dart.dartgame.gui.app.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherService {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EventPublisherService.class);

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    public void publish(ApplicationEvent event) {
        LOG.debug("Publishing event: {}", event.getClass().getName());
        applicationContext.publishEvent(event);
    }
}
