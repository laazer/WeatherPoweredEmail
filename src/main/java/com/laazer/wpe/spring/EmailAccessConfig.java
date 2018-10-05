package com.laazer.wpe.spring;

import com.laazer.wpe.dao.EmailAccessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Laazer
 */
@Configuration
public class EmailAccessConfig {

    @Bean
    public EmailAccessor emailAccessor() {
        return new EmailAccessor();
    }
}
