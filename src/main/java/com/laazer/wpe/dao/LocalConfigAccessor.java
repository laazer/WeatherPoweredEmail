package com.laazer.wpe.dao;

import com.laazer.wpe.internal.exception.BeanInitException;
import com.laazer.wpe.util.ExceptionUtil;

import java.io.IOException;
import java.util.Properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
public class LocalConfigAccessor {

    private final Properties configFile;

    public LocalConfigAccessor(final String configPath) throws BeanInitException {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream(configPath));
        } catch(final IOException eta){
            ExceptionUtil.beanInitException(log, eta, "Failed to init {0}",
                    LocalConfigAccessor.class.getName());

        }
    }

    public String getProperty(final Config key) {
        final String value = this.configFile.getProperty(key.getValue());
        return value;
    }

    public enum Config {
        GEO_API_KEY("geoApiKey"),
        ZIP_CODE_API_KEY("zipCodeApiKey"),
        EMAIL_UNAME("emailUname"),
        EMAIL_PWD("emailPassword");

        @Getter
        private final String value;
        Config(final String value) {
            this.value = value;
        }
    }
}
