package com.laazer.wpe.spring;

import com.google.maps.GeoApiContext;

import com.laazer.wpe.dao.IZipCodeAccessor;
import com.laazer.wpe.dao.LocalConfigAccessor;
import com.laazer.wpe.dao.GoogleMapsAccessor;
import com.laazer.wpe.dao.WeatherAccessor;
import com.laazer.wpe.dao.ZipCodeAccessor;
import com.laazer.wpe.internal.exception.BeanInitException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
@Configuration
public class DataAccessConfig {


    @Autowired
    private AppConfig appConfig;

    @Bean
    public GeoApiContext geoApiContext() throws BeanInitException {
        final String key = appConfig
                .localConfigAccessor().getProperty(LocalConfigAccessor.Config.GEO_API_KEY);
        return new GeoApiContext.Builder().apiKey(key).build();
    }

    //@Bean
    public GoogleMapsAccessor googleMapsAccessor() throws BeanInitException {
        return new GoogleMapsAccessor(geoApiContext());
    }

    @Bean
    public ZipCodeAccessor aZipCodeAccessor() throws BeanInitException {
        return new ZipCodeAccessor(appConfig.localConfigAccessor()
                .getProperty(LocalConfigAccessor.Config.ZIP_CODE_API_KEY));
    }

    @Bean
    public WeatherAccessor aWeatherAccessor() throws BeanInitException {
        return new WeatherAccessor(appConfig.localConfigAccessor()
                .getProperty(LocalConfigAccessor.Config.WEATHER_API_KEY));
    }

    @Bean
    public IZipCodeAccessor zipCodeAccessor() throws BeanInitException {
        return aZipCodeAccessor();
    }


}
