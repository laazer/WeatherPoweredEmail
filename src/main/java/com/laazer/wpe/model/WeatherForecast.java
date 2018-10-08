package com.laazer.wpe.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Laazer
 */
@Data
@Builder
public class WeatherForecast {

    private final Date date;
    private double avgTemp;
    private double maxTemp;
    private double minTemp;
    private int humidity;
    private int cloudCover;
    private double snow3hVolume;
    private double rain3hVolume;
    private double windSpeed; // meters / sec
    private double windDirection; // degrees
    private double pressure;
    private String descSmall;
    private String descLarge;

    public String getWeatherSummary() {
        //TODO
        return null;
    }
}
