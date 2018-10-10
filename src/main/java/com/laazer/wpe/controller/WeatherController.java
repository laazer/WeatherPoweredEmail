package com.laazer.wpe.controller;

import com.laazer.wpe.dao.WeatherAccessor;
import com.laazer.wpe.db.UserRepository;
import com.laazer.wpe.model.DetailedMultiDayWeatherForecast;
import com.laazer.wpe.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
@Controller
public class WeatherController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherAccessor weatherAccessor;

    @GetMapping("weather/{id}")
    public String getWeather(@PathVariable final String id, Model model) {
        log.info("Getting user id for {}", id);
        final User user = this.userRepository.findById(id + ".com").get();
        final DetailedMultiDayWeatherForecast forecast = this.weatherAccessor
                .getDetailedWeather(user.getZipCode(), user.getTimeZone());
        model.addAttribute("user", user);
        model.addAttribute("today", forecast.getDisplayData().get(0));
        model.addAttribute("mdf", forecast.getDisplayData());
        return "email/weatherEmail";
    }
}
