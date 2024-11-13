package com.bognandi.dartgame.app.config;

import com.bognandi.dartgame.domain.dartboard.DartboardFactory;
import com.bognandi.dartgame.domain.dartboard.bluetooth.BluetoothDartboardFactory;
import com.bognandi.dartgame.domain.dartgame.DartgameFactory;
import com.bognandi.dartgame.domain.dartgame.DefaultDartgameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public DartboardFactory createDartboardFactory() {
        return new BluetoothDartboardFactory();
    }

    @Bean
    public DartgameFactory createDartgameFactory() {
        return new DefaultDartgameFactory();
    }
}
