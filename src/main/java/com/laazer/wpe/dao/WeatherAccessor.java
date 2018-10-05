package com.laazer.wpe.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Laazer
 */
public class WeatherAccessor {

    private static final String PATH = "api.openweathermap.org/data/2.5/forecast?zip={0},us&appid={1}";
    private final String apiKey;

    public WeatherAccessor(final String apiKey) {
        this.apiKey = apiKey;
    }

    public String getWeather(final String location) {
        final RestTemplate restTemplate = new RestTemplate();
        final MultiDayWeatherForecast forecast = restTemplate.getForObject(this.getPath(location),
                MultiDayWeatherForecast.class);
        //TODO
        return null;
    }
    public String getPath(final String zipCode) {
        return MessageFormat.format(PATH, zipCode, this.apiKey);
    }

    /**
     * Wrapper classes for responses from the weather api;
     */

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MultiDayWeatherForecast {

    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherForecast {

    }
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class TempWeatherData {

    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherForecast {

    }
}
