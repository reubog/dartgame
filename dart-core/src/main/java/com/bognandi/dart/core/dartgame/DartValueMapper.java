package com.bognandi.dart.core.dartgame;

public interface DartValueMapper {
    int getDartScore(Dart dart);
    int getDartValue(Dart dart);
    int multiplier(Dart dart);
}
