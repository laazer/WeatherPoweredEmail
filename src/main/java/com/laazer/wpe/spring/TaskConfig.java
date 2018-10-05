package com.laazer.wpe.spring;

import com.laazer.wpe.tasks.EmailTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Laazer
 */
@Configuration
public class TaskConfig {

    public static final long EMAIL_TASK_RATE_MS = TimeUnit.HOURS.toMillis(1);
    public static final String EMAIL_TASK_RATE_CRON = "0 * * * *";

    @Autowired
    private EmailAccessConfig emailAccessConfig;

    @Bean
    public EmailTask emailTask() {
        return new EmailTask(emailAccessConfig.emailAccessor());
    }
}
