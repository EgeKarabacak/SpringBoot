package com.team20.core;

import java.time.LocalDateTime;

public class TimeController {
    
    public static LocalDateTime currTime = LocalDateTime.now();

    public static void changeSysTime(LocalDateTime newTime) {
        currTime = newTime;
    }

    public static void advanceDay() {
        currTime.plusDays(1);
    }

    public static void subtractDay() {
        currTime.minusDays(1);
    }

    public static LocalDateTime getTime() {return currTime;}

}