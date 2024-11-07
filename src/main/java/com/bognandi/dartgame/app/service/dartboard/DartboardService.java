package com.bognandi.dartgame.app.service.dartboard;

import com.bognandi.dartgame.domain.dartboard.DartboardFactory;
import com.bognandi.dartgame.domain.dartgame.Dartboard;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class DartboardService {

    private static final Logger LOG = LoggerFactory.getLogger(DartboardService.class);

    private DartboardFactory dartboardFactory;

    public DartboardService(@Autowired DartboardFactory dartboardFactory) {
        LOG.info("Constructing service");
        this.dartboardFactory = dartboardFactory;
    }

    public void findDartboard(String dartboardName, Consumer<Dartboard> dartboardConsumer) {
        dartboardFactory.createDartboard(dartboardName, dartboardConsumer);
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Destroying!");
        dartboardFactory.shutdown();
    }


}
