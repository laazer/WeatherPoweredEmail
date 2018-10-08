package com.laazer.wpe.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.laazer.wpe.model.MultiDayWeatherForecast;
import com.laazer.wpe.model.WeatherForecast;

import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Laazer
 */
public class WeatherAccessor {

    private static final String PATH = "http://api.openweathermap.org/data/2.5/forecast?zip={0},us&units={1}&appid={2}";
    private static final String DEFAULT_TEMP_UNIT = "imperial";
    private final String apiKey;

    public WeatherAccessor(final String apiKey) {
        this.apiKey = apiKey;
    }

    public MultiDayWeatherForecast getWeather(final String location) {
        final RestTemplate restTemplate = new RestTemplate();
        final MultiDayWeatherForecastData forecast = restTemplate.getForObject(this.getPath(location),
                MultiDayWeatherForecastData.class);
        return forecast == null ? null : toForecastModel(forecast);
    }
    private String getPath(final String zipCode) {
        return MessageFormat.format(PATH, zipCode, DEFAULT_TEMP_UNIT, this.apiKey);
    }

    private static MultiDayWeatherForecast toForecastModel(final MultiDayWeatherForecastData data) {
        final MultiDayWeatherForecast result = new MultiDayWeatherForecast();
        final List<WeatherForecastData> forecastData = Arrays.asList(data.getList());
        forecastData.stream().sorted((a, b) -> (int) (a.getDt() - b.getDt()))
                .forEach(f -> {
                    final WeatherForecast forecast = WeatherForecast.builder()
                            .date(new Date(f.getDt()))
                            .avgTemp(f.getMain().getTemp())
                            .minTemp(f.getMain().getTemp_min())
                            .maxTemp(f.getMain().getTemp_max())
                            .humidity(f.getMain().getHumidity())
                            .cloudCover(f.getClouds() != null ? f.getClouds().getAll() : 0)
                            .windDirection(f.getWind() != null ? f.getWind().getDeg() : 0)
                            .windSpeed(f.getWind() != null ? f.getWind().getSpeed() : 0)
                            .rain3hVolume(f.getRain() != null ? f.getRain().getVolume_3h() : 0)
                            .snow3hVolume(f.getSnow() != null ? f.getSnow().getVolume_3h() : 0)
                            .descSmall(Arrays.asList(f.getWeather()).stream().findFirst().map(WeatherInfo::getMain).orElse(null))
                            .descLarge(Arrays.asList(f.getWeather()).stream().findFirst().map(WeatherInfo::getDescription).orElse(null))
                            .build();
                    result.addWeatherForecast(forecast);
                });
        return result;
    }

    /**
     * Wrapper classes for responses from the weather api;
     * https://openweathermap.org/forecast5#JSON
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MultiDayWeatherForecastData {
        private WeatherForecastData[] list;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WeatherForecastData {
        private long dt;
        private String dt_txt;
        private TempWeatherData main;
        private WeatherInfo[] weather;
        private CloudData clouds;
        private WindData wind;
        private SnowData snow;
        private RainData rain;
    }
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TempWeatherData {
        private double temp;
        private double temp_min;
        private double temp_max;
        private int humidity; // humidity %
        private double pressure; // atmospheric pressure
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WeatherInfo {
        private String main;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CloudData {
        private int all; //cloudiness %
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class RainData {
        @JsonProperty(value = "3h")
        private double volume_3h;
    }

    @lombok.Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SnowData {
        @JsonProperty(value = "3h")
        private double volume_3h;
    }
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WindData {
        private double speed;
        private double deg;
    }
}
