package com.laazer.wpe.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.laazer.wpe.model.Coordinate;
import com.laazer.wpe.model.DayTempData;
import com.laazer.wpe.model.DayWeatherForecast;
import com.laazer.wpe.model.DetailedMultiDayWeatherForecast;
import com.laazer.wpe.model.SimpleMultiDayForecast;
import com.laazer.wpe.model.ThreeHourWeatherForecast;
import com.laazer.wpe.model.WeatherInfo;
import com.laazer.wpe.model.WindData;

import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
public class WeatherAccessor {

    private static final String DETAILED_PATH = "http://api.openweathermap.org/data/2.5/forecast?zip={0},us&units={1}&appid={2}";
    private static final String SIMPLE_PATH = "http://api.openweathermap.org/data/2.5/forecast/daily?zip={0},us&units={1}&appid={2}";
    private static final String DEFAULT_TEMP_UNIT = "imperial";
    private final String apiKey;

    public WeatherAccessor(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get the weather for the given zip code. The given time zone is applied to the dates to localize to the correct time zone.
     * If the given time zone is null, dates will be localized to UTC.
     * @param location zzp code
     * @param timeZone time zone to localize to. null value localizes to UTC
     * @return the weather for the given zip code. The given time zone is applied to the dates to localize to the correct time zone.
     * If the given time zone is null, dates will be localized to UTC.
     */
    public DetailedMultiDayWeatherForecast getDetailedWeather(final String location, final String timeZone) {
        final RestTemplate restTemplate = new RestTemplate();
        final DetailedMultiDayWeatherForecastData forecast = restTemplate.getForObject(this.getPath(DETAILED_PATH, location),
                DetailedMultiDayWeatherForecastData.class);
        return forecast == null ? null : toDetailedForecastModel(forecast, timeZone);
    }

    /**
     * Get the weather for the given zip code.
     * NOTE: requires paid tier
     *
     * @param location zzp code
     * @return the weather for the given zip code.
     */
    public SimpleMultiDayForecast getSimpleWeather(final String location) {
        final RestTemplate restTemplate = new RestTemplate();
        log.info(this.getPath(SIMPLE_PATH, location));
        final SimpleMultiDayWeatherForecastData forecast = restTemplate.getForObject(this.getPath(SIMPLE_PATH, location),
                SimpleMultiDayWeatherForecastData.class);
        return forecast == null ? null : toSimpleForecastModel(forecast);
    }

    private String getPath(final String path, final String zipCode) {
        return MessageFormat.format(path, zipCode, DEFAULT_TEMP_UNIT, this.apiKey);
    }

    private static SimpleMultiDayForecast toSimpleForecastModel(final SimpleMultiDayWeatherForecastData data) {
        final SimpleMultiDayForecast result = new SimpleMultiDayForecast();
        final List<SimpleWeatherForecastData> weekForecast = Arrays.asList(data.getList());
        result.setCity(data.getCityData().getName());
        result.setCountry(data.getCountry());
        result.setCoord(data.getCoord());
        weekForecast.stream().sorted((a, b) -> (int) (a.getDt() - b.getDt())).forEach(f -> {
            final LocalDate date = LocalDate.ofEpochDay(TimeUnit.SECONDS.toDays(f.getDt()));
            final WindData windData = new WindData(f.getSpeed(), f.getDeg());
            final DayWeatherForecast weatherForecast = DayWeatherForecast.builder()
                    .day(date)
                    .humidity(f.getHumidity())
                    .pressure(f.getPressure())
                    .tempData(f.getMain())
                    .windData(windData)
                    .build();
            result.getWeekForecast().add(weatherForecast);
        });
        return result;
    }

    private static DetailedMultiDayWeatherForecast toDetailedForecastModel(final DetailedMultiDayWeatherForecastData data, final String timeZone) {
        final DetailedMultiDayWeatherForecast result = new DetailedMultiDayWeatherForecast();
        final List<DetailedWeatherForecastData> forecastData = Arrays.asList(data.getList());
        final ZoneId zoneId = timeZone == null ? ZoneId.of("UTC") : ZoneId.of(timeZone);
        forecastData.stream().sorted((a, b) -> (int) (a.getDt() - b.getDt()))
                .forEach(f -> {
                    final LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(f.getDt() * 1000), zoneId);
                    final ThreeHourWeatherForecast forecast = ThreeHourWeatherForecast.builder()
                            .date(ldt)
                            .curTemp(f.getMain().getTemp())
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
                            .icon(Arrays.asList(f.getWeather()).stream().findFirst().map(WeatherInfo::getIcon).orElse(null))
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
    private static class DetailedMultiDayWeatherForecastData {
        private DetailedWeatherForecastData[] list;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DetailedWeatherForecastData {
        private long dt;
        private String dt_txt;
        private TempWeatherData main;
        private WeatherInfo[] weather;
        private CloudData clouds;
        private WindData wind;
        private SnowData snow;
        private RainData rain;
        private CityData city;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SimpleMultiDayWeatherForecastData {
        private SimpleWeatherForecastData[] list;
        private CityData cityData;
        private String country;
        private Coordinate coord;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SimpleWeatherForecastData {
        private long dt;
        private DayTempData main;
        private WeatherInfo[] weather;
        private int humidity; // humidity %
        private double pressure; // atmospheric pressure
        private double speed;
        private double deg;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TempWeatherData {
        private double temp; //average temp or cur temp if for the current period.
        private double temp_min;
        private double temp_max;
        private int humidity; // humidity %
        private double pressure; // atmospheric pressure
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
    private static class CityData {
        private String name;
        private String country;
    }
}
