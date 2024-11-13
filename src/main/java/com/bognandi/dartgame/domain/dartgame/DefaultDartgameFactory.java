package com.bognandi.dartgame.domain.dartgame;

import com.bognandi.dartgame.domain.x01game.X01Dartgame;
import com.bognandi.dartgame.domain.x01game.X01ScoreBoard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DefaultDartgameFactory implements DartgameFactory {

    private static final List<DartgameDescriptor> DARTGAMES = Arrays.asList(
            new DefaultDartgameDescriptor("301", "301", ""),
            new DefaultDartgameDescriptor("501", "501", ""),
            new DefaultDartgameDescriptor("701", "701", "")
    );

    private static final Map<String, Supplier<Dartgame>> GAME_SUPPLIERS = Map.of(
            "301", () -> new X01Dartgame(new X01ScoreBoard(301, new DefaultDartValueMapper())),
            "501", () -> new X01Dartgame(new X01ScoreBoard(501, new DefaultDartValueMapper())),
            "701", () -> new X01Dartgame(new X01ScoreBoard(701, new DefaultDartValueMapper()))
    );

    @Override
    public List<DartgameDescriptor> getDartgames() {
        return Collections.unmodifiableList(DARTGAMES);
    }

    @Override
    public Dartgame createDartgame(String id) {
        return GAME_SUPPLIERS.get(id).get();
    }
}
