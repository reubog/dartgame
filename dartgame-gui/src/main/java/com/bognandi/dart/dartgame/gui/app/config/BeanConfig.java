package com.bognandi.dart.dartgame.gui.app.config;

import com.bognandi.dart.core.dartgame.DartValueMapper;
import com.bognandi.dart.core.dartgame.DartgameFactory;
import com.bognandi.dart.core.dartgame.DefaultDartValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    //public static final DartgameFactory DARTGAME_FACTORY = new DefaultDartgameFactory();

//    @Bean
//    public DartgameFactory createDartgameFactory() {
//        return DARTGAME_FACTORY;
//    }

    @Bean
    public DartValueMapper createDartValueMapper() {
        return new DefaultDartValueMapper();
    }

}
