package com.laazer.wpe.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Laazer
 */
@Data
@ToString
public class DetailedMultiDayWeatherForecast {

    private final Map<LocalDate, List<ThreeHourWeatherForecast>> forecast;
    private String city;
    private String country;

    public DetailedMultiDayWeatherForecast() {
        this.forecast = new TreeMap<>();
    }

    public void addWeatherForecast(final ThreeHourWeatherForecast weatherForecast) {
        final List<ThreeHourWeatherForecast> value = this.forecast
                .computeIfAbsent(LocalDate.from(weatherForecast.getDate()), k -> new ArrayList<>());
        final int idx = Math.abs(Collections.binarySearch(value, weatherForecast,
                Comparator.comparing(ThreeHourWeatherForecast::getDate)));
        if (value.isEmpty() || idx > value.size()) {
            value.add(weatherForecast);
        } else {
            // keep the list ordered
            value.add(idx, weatherForecast);
        }
    }

    public List<WeatherDisplayData> getDisplayData() {
        return this.forecast.values().stream()
//                .sorted(LocalDate::compareTo)
//                .map(k -> forecast.get(k))
                .map(f -> WeatherDisplayData.builder()
                .dayName(f.stream().findFirst().map(ThreeHourWeatherForecast::forecastDayName).orElse(null))
                .desc(f.stream().findFirst().map(ThreeHourWeatherForecast::getDescSmall).orElse(null))
                .icon(f.stream().findFirst().map(ThreeHourWeatherForecast::getIconSrc).orElse(null))
                .maxTemp(f.stream().mapToDouble(ThreeHourWeatherForecast::getMaxTemp).max().getAsDouble())
                .minTemp(f.stream().mapToDouble(ThreeHourWeatherForecast::getMaxTemp).min().getAsDouble())
                .build()).collect(Collectors.toList());
    }
}
