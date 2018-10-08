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

    public MultiDayWeatherForecast() {
        this.forecast = new ArrayList<>();
    }

    public void addWeatherForecast(final WeatherForecast weatherForecast) {
        this.forecast.add(weatherForecast);
    }

    public String getWeatherSummary() {
        //TODO
        return null;
    }
}
