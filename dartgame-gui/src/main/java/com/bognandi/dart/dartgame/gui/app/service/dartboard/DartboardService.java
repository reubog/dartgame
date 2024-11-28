package com.bognandi.dart.dartgame.gui.app.service.dartboard;

import com.bognandi.dart.core.dartboard.DartboardFactory;
import com.bognandi.dart.core.dartgame.Dartboard;
import com.bognandi.dart.dartboard.mqtt.MqttDartboardFactory;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DartboardService {

    private static final Logger LOG = LoggerFactory.getLogger(DartboardService.class);

    private DartboardFactory dartboardFactory;

    public DartboardService(
            @Value("${dartboard.mqtt.serverUrl}") String serverUrl,
            @Value("${dartboard.mqtt.clientId}") String clientId
    ) {
        LOG.info("Constructing service");
        this.dartboardFactory = new MqttDartboardFactory(serverUrl, clientId);
    }

    public Dartboard createDartboard() {
        LOG.info("Creating dartboard");
        return dartboardFactory.createDartboard();
    }


    @PreDestroy
    public void destroy() {
        LOG.info("Destroying!");
        dartboardFactory.shutdown();
    }


}
