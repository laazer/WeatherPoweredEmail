package com.laazer.wpe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Laazer
 */
@Data
@ToString
public class MultiDayWeatherForecast {

    private final List<WeatherForecast> forecast;
    private String city;
    private String country;

    public MultiDayWeatherForecast() {
        this.forecast = new ArrayList<>();
    }

    public void addWeatherForecast(final WeatherForecast weatherForecast) {
        this.forecast.add(weatherForecast);
    }

    public WeatherForecast getToday() {
        return this.forecast.stream().findFirst().orElse(null);
    }

    public String getWeatherSummary() {
        //TODO
        return this.toString();
    }
}
