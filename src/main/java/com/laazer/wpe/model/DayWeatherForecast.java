package com.laazer.wpe.model;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Laazer
 */
@Data
@Builder
public class DayWeatherForecast {

    private static final String ICON_SRC_FORMAT = "http://openweathermap.org/img/w/{0}.png";

    private LocalDate day;
    private DayTempData tempData;
    private WeatherInfo weatherInfo;
    private WindData windData;
    private double pressure;
    private int humidity;

    public String forecastDayName() {
        return DayOfWeek.from(this.day).getDisplayName(TextStyle.FULL, Locale.US);
    }

    public String getIconSrc() {
        return MessageFormat.format(ICON_SRC_FORMAT, this.weatherInfo.getIcon());
    }
}
