package com.laazer.wpe.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeZoneUtil {

    public static final String US_TZ = "America";

    private static final String DELIMETER = "/";

    private static final Calendar CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    /**
     * Get the list of time zones that have the given utc offset.
     * @param offsetHour  hour offset
     * @param offsetMin minute offset
     * @param rounder function for rounding the minute.
     * @return the list of time zones that have the given utc offset.
     */
    private static List<String> getTimeForCurrentOffset(int offsetHour, int offsetMin,
                                                       final Function<Integer, Integer> rounder) {
        final int hour = offsetHour * 60 * 60 * 1000;
        final int minute = offsetMin * 60 * 1000;
        final int offset = rounder.apply(hour + minute);
        final List<String> tzs = Arrays.asList(TimeZone.getAvailableIDs(offset));
        log.info("Found {} for utc offset {} min", tzs,  offset);
        return tzs;
    }

    /**
     * Get the list of time zones that have the given utc offset.
     * @param offsetHour  hour offset
     * @param offsetMin minute offset
     * @return the list of time zones that have the given utc offset.
     */
    public static List<String> getTimeForCurrentOffset(int offsetHour, int offsetMin) {
        return getTimeForCurrentOffset(offsetHour, offsetMin, Function.identity());
    }

    /**
     * Get the list of time zones that have the given utc offset rounding to the closest
     * 15 minute offset.
     * @param offsetHour  hour offset
     * @param offsetMin minute offset
     * @return the list of time zones that have the given utc offset.
     */
    public static List<String> getTimeForCurrentOffsetRound15(int offsetHour, int offsetMin) {
        return getTimeForCurrentOffset(offsetHour, offsetMin, m -> 15 * (offsetMin / 15));
    }

    /**
     * Get the minutes until the given time out day for the UTC TimeZone.
     * @param hour hour of day until.
     * @param minute minute of day until.
     * @return the minutes until the given time out day for the UTC TimeZone.
     */
    public static int getMinUntilUtc(final int hour, final int minute) {
        assert(hour < 24);
        assert(minute < 60);
        int hourOfDay = CALENDAR.get(Calendar.HOUR_OF_DAY);
        int minuteOfHour = CALENDAR.get(Calendar.MINUTE);
        int result = 0;
        if (hour > hourOfDay) {
            result = (hour - hourOfDay) * 60;
        } else {
            result = (hour + hourOfDay % 24) * 60;
        }
        if (minute > minuteOfHour) {
            result +=  minute - minuteOfHour;
        } else {
            result += (60 - minute) + minuteOfHour;
        }
        
        return result;
    }

    public static String getCurrentDayOfWeekForTimeZone(final String tz) {
        final ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(tz));
        return DayOfWeek.from(zdt).getDisplayName(TextStyle.FULL, Locale.US);
    }

}
