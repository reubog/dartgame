package com.bognandi.dart.core.dartgame;

public class DefaultDartgameDescriptor implements DartgameDescriptor {

    private final String id;
    private final String name;
    private final String description;

    public DefaultDartgameDescriptor(String id, String name, String  description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescrption() {
        return description;
    }
}
