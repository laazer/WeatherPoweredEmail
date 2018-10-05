package com.laazer.wpe.tasks;

import com.laazer.wpe.dao.EmailAccessor;
import com.laazer.wpe.dao.WeatherAccessor;
import com.laazer.wpe.db.UserRepository;
import com.laazer.wpe.model.Email;
import com.laazer.wpe.model.User;
import com.laazer.wpe.util.TimeZoneUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by Laazer
 */
public class EmailTask {

    private static final int DEFAULT_SEND_HOUR = 6;
    private static final int DEFAULT_SEND_MIN = 0;
    private final String senderId;
    private final EmailAccessor emailAccessor;
    private final UserRepository userRepository;
    private final WeatherAccessor weatherAccessor;

    public EmailTask(final String senderId,
                     final EmailAccessor emailAccessor,
                     final UserRepository userRepository,
                     final WeatherAccessor weatherAccessor) {
        this.senderId = senderId;
        this.emailAccessor = emailAccessor;
        this.userRepository = userRepository;
        this.weatherAccessor = weatherAccessor;
    }

    public void run() {
        final int minUntilSendTime = TimeZoneUtil
                .getMinUntilUtc(DEFAULT_SEND_HOUR, DEFAULT_SEND_MIN);
        final List<String> activeTzs = TimeZoneUtil.getTimeForCurrentOffsetRound15(0,
                minUntilSendTime);
        activeTzs.forEach(tz -> {
            final List<User> users = this.userRepository.findUserByTimeZone(tz);
            users.forEach(user -> this.emailAccessor.sendEmail(this.makeEmailForUser(user)));
        });
    }

    private Email makeEmailForUser(final User user) {
        final String weather = weatherAccessor.getWeather(user.getLocation());
        return Email.builder()
                .recipients(Collections.singletonList(user.getEmail()))
                .sender(this.senderId)
                .text(weather)
                .build();
    }
}