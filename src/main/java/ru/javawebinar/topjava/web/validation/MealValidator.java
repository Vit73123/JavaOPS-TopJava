package ru.javawebinar.topjava.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MealValidator implements Validator {

    @Autowired
    MealRepository repository;

    @Autowired
    MessageSource messageSource;

    private HttpServletRequest request;

    @Override
    public boolean supports(Class clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        Meal meal = (Meal) target;
        LocalDateTime dateTime = meal.getDateTime();

        try {
            if (isNew() && repository.getBetweenHalfOpen(dateTime, null, SecurityUtil.authUserId()).isEmpty()) {
                String dateTimeErrorCode = "meal.error.dateTime.exists";
                errors.rejectValue("dateTime",
                        dateTimeErrorCode,
                        messageSource.getMessage(dateTimeErrorCode, null, request.getLocale()));
            }
        } catch (Exception ignored) {
        }
    }

    private boolean isNew() {
        return !request.getMethod().equals("PUT");
    }
}
