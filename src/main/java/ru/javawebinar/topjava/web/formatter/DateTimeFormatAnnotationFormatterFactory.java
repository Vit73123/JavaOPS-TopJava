package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(List.of(LocalDate.class, LocalTime.class));
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class fieldType) {
        return getFormatter(annotation, fieldType);
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class fieldType) {
        return getFormatter(annotation, fieldType);
    }

    private Formatter<?> getFormatter(DateTimeFormat annotation, Class<?> fieldType) {
        switch (annotation.type()) {
            case DATE -> {
                return new DateFormatter();
            }
            case TIME -> {
                return new TimeFormatter();
            }
        }
        throw new IllegalArgumentException("Date/Time formatter annotation error: " + annotation.type());
    }
}
