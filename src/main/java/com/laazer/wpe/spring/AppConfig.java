package com.laazer.wpe.spring;

import com.laazer.wpe.dao.LocalConfigAccessor;
import com.laazer.wpe.internal.exception.BeanInitException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Laazer
 */
@Configuration
public class AppConfig {

    private static final String WPE_CONFIG_PATH = "/configuration/WeatherPoweredEmail.cfg";

    @Bean
    public LocalConfigAccessor localConfigAccessor() throws BeanInitException {
        return new LocalConfigAccessor(WPE_CONFIG_PATH);
    }
}
