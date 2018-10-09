package com.laazer.wpe.spring;

import com.laazer.wpe.dao.EmailAccessor;
import com.laazer.wpe.dao.LocalConfigAccessor;
import com.laazer.wpe.internal.exception.BeanInitException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Created by Laazer
 */
@Configuration
public class EmailAccessConfig {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Bean
    public EmailAccessor emailAccessor() throws BeanInitException {
        final LocalConfigAccessor configAccessor = appConfig.localConfigAccessor();
        final String host = configAccessor.getProperty(LocalConfigAccessor.Config.EMAIL_SMTP_SERVER);
        final String port = configAccessor.getProperty(LocalConfigAccessor.Config.EMAIL_HOST_PORT);
        final String uname = configAccessor.getProperty(LocalConfigAccessor.Config.EMAIL_UNAME);
        final String passowrd = configAccessor.getProperty(LocalConfigAccessor.Config.EMAIL_PWD);
        return new EmailAccessor(host, port, uname, passowrd, templateEngine);
    }
}
