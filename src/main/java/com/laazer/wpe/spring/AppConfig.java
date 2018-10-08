package com.laazer.wpe.spring;

import com.laazer.wpe.dao.LocalConfigAccessor;
import com.laazer.wpe.internal.exception.BeanInitException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
@Configuration
public class AppConfig {

    private static final String WPE_CONFIG_PATH = "/configuration/WeatherPoweredEmail.cfg";
    private static final String DEFAULT_CONFIG_PATH = "/configuration/Default.cfg";

    @Bean
    public LocalConfigAccessor localConfigAccessor() throws BeanInitException {
        // Use default conifgs if the project specific ones cant be found.
        try {
            return new LocalConfigAccessor(WPE_CONFIG_PATH);
        } catch (final BeanInitException e) {
            log.warn("Could not initialize WPE AppConfig, trying with Defaults.");
            return new LocalConfigAccessor(DEFAULT_CONFIG_PATH);
        }

    }
}
