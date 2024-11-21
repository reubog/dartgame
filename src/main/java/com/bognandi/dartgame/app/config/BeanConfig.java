package com.bognandi.dartgame.app.config;

import com.bognandi.dartgame.domain.dartboard.DartboardFactory;
import com.bognandi.dartgame.domain.dartboard.bluetooth.BluetoothDartboardFactory;
import com.bognandi.dartgame.domain.dartgame.DartValueMapper;
import com.bognandi.dartgame.domain.dartgame.DartgameFactory;
import com.bognandi.dartgame.domain.dartgame.DefaultDartValueMapper;
import com.bognandi.dartgame.domain.dartgame.DefaultDartgameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    public static final DartgameFactory DARTGAME_FACTORY = new DefaultDartgameFactory();

//    @Bean
    public DartboardFactory createDartboardFactory() {
        return new BluetoothDartboardFactory();
    }

    @Bean
    public DartgameFactory createDartgameFactory() {
        return DARTGAME_FACTORY;
    }

    @Bean
    public DartValueMapper createDartValueMapper() {
        return new DefaultDartValueMapper();
    }
}
