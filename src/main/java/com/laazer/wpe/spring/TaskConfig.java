package com.laazer.wpe.spring;

import com.laazer.wpe.dao.LocalConfigAccessor;
import com.laazer.wpe.internal.exception.BeanInitException;
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
    public static final String TEST_CRON = "0 */10 * ? * *";
    public static final String EMAIL_TASK_RATE_CRON = "0 0 0/1 ? * *";

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private EmailAccessConfig emailAccessConfig;

    @Autowired
    private DataAccessConfig dataAccessConfig;

    @Bean
    public EmailTask emailTask() throws BeanInitException {
        return new EmailTask(appConfig.localConfigAccessor().getProperty(LocalConfigAccessor.Config.WEATHER_API_KEY),
                emailAccessConfig.emailAccessor(),
                dataAccessConfig.getUserRepository(),
                dataAccessConfig.aWeatherAccessor());
    }
}
