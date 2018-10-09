package com.laazer.wpe.model;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Laazer
 */
@Data
@Builder
public class WeatherForecast {

    private static final String ICON_SRC_FORMAT = "http://openweathermap.org/img/w/{0}.png";

    private final LocalDateTime date;
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
    private String icon;

    public String getWeatherSummary() {
        //TODO
        return null;
    }

    public String forecastDayName() {
        return DayOfWeek.from(this.date).getDisplayName(TextStyle.FULL, Locale.US);
    }

    public String getIconSrc() {
        return MessageFormat.format(ICON_SRC_FORMAT, this.getIcon());
    }
}
