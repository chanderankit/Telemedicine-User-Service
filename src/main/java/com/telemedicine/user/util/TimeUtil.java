package com.telemedicine.user.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeUtil {
    public void compareTime() {
        String strTime = "12:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(strTime, formatter);
        log.info("Resultant Date and Time = {}" , localTime);

        LocalTime now = LocalTime.now();
        String currentTime = now.format(formatter);
        log.info("current Date and Time = {} ", currentTime);

        LocalTime storedTime = LocalTime.parse(strTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime currentTimee = LocalTime.now();

        log.info("result  -> {}", currentTime.compareTo(String.valueOf(storedTime)) > 0);

        if (currentTime.compareTo(String.valueOf(storedTime)) > 0) {
            // current time is after stored time
        } else if (currentTime.compareTo(String.valueOf(storedTime)) < 0) {
            // current time is before stored time
        } else {
            // current time is the same as stored time
        }

    }
}
