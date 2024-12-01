package com.bognandi.dart.dartgame.gui.app.service;

import com.bognandi.dart.core.dartgame.Dartgame;
import com.bognandi.dart.core.dartgame.DartgameDescriptor;
import com.bognandi.dart.core.dartgame.DartgameFactory;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DartgamesService {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DartgamesService.class);

    private Map<DartgameDescriptor,DartgameFactory> availableDartgames;

    public DartgamesService() {
        LOG.info("Scanning for dartgames...");

        try (ScanResult scanResult = new ClassGraph().acceptPackages("").scan()) {
            ClassInfoList list = scanResult.getClassesImplementing(DartgameFactory.class);
            availableDartgames = list.loadClasses().stream()
                    .map(clazz -> {
                        try {
                            DartgameFactory factory = (DartgameFactory) clazz.getDeclaredConstructor().newInstance();
                            LOG.info("Found dartgame: " + factory.getDartgameDescriptor().getTitle());
                            return factory;
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toMap(
                            factory -> factory.getDartgameDescriptor(),
                            factory -> factory
                    ));
        }
    }

    public List<DartgameDescriptor> getDartgames() {
        return availableDartgames.keySet().stream().toList();
    }

    public Dartgame createDartgame(DartgameDescriptor dartgameDescriptor) {
        return availableDartgames.get(dartgameDescriptor).createDartgame();
    }
}
