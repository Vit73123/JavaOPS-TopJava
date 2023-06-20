package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final LocalDate MAX_DATE =  LocalDate.MAX.minusDays(1);
    public static final LocalDate MIN_DATE =  LocalDate.MIN;
    public static final LocalTime MIN_TIME = LocalTime.MIDNIGHT;
    public static final LocalTime MAX_TIME =  LocalTime.MAX;


    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T ldt, T start, T end) {
        return ldt.compareTo(start) >= 0 && ldt.compareTo(end) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

