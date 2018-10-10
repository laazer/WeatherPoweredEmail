package com.laazer.wpe.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Laazer
 */
@Data
@Builder
public class WeatherDisplayData {
    private String icon;
    private String dayName;
    private double maxTemp;
    private double minTemp;
    private String desc;
}
