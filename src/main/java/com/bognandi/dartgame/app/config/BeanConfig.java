package com.bognandi.dartgame.app.config;

import com.bognandi.dartgame.domain.dartboard.DartboardFactory;
import com.bognandi.dartgame.domain.dartboard.bluetooth.BluetoothDartboardFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public DartboardFactory createDartboardFactory() {
        return new BluetoothDartboardFactory();
    }
}
