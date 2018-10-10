package com.laazer.wpe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Laazer
 */
@Data
public class SimpleMultiDayForecast {
    private List<DayWeatherForecast> weekForecast;
    private String city;
    private String country;
    private Coordinate coord;

    public SimpleMultiDayForecast() {
        this.weekForecast = new ArrayList<>();
    }
}
