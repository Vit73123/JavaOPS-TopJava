package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.parse(text, dateFormatter);
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.toString();
    }
}
