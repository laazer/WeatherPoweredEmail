package com.laazer.wpe.model;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Laazer
 */
@Data
@ToString
public class DetailedMultiDayWeatherForecast {

    private final Map<String, List<ThreeHourWeatherForecast>> forecast;
    private String city;
    private String country;

    public DetailedMultiDayWeatherForecast() {
        this.forecast = new HashMap<>();
    }

    public void addWeatherForecast(final ThreeHourWeatherForecast weatherForecast) {
        this.forecast.computeIfAbsent(getSimpleStamp(weatherForecast.getDate()), k -> new ArrayList<>())
                .add(weatherForecast);
    }

    private static String getSimpleStamp(final LocalDateTime dateTime) {
        return MessageFormat.format("{1}{0}{2}{0}{3}", "_", dateTime.getYear(),
                dateTime.getMonthValue(), dateTime.getDayOfMonth());
    }

    public List<WeatherDisplayData> getDisplayData() {
        return this.forecast.values().stream().map(f -> WeatherDisplayData.builder()
                .dayName(f.stream().findFirst().map(ThreeHourWeatherForecast::forecastDayName).orElse(null))
                .desc(f.stream().findFirst().map(ThreeHourWeatherForecast::getDescSmall).orElse(null))
                .icon(f.stream().findFirst().map(ThreeHourWeatherForecast::getIconSrc).orElse(null))
                .maxTemp(f.stream().mapToDouble(ThreeHourWeatherForecast::getMaxTemp).max().getAsDouble())
                .minTemp(f.stream().mapToDouble(ThreeHourWeatherForecast::getMaxTemp).min().getAsDouble())
                .build()).collect(Collectors.toList());
    }
}
