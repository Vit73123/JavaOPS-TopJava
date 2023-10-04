package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserService service;

    @Autowired
    MessageSource messageSource;

    private HttpServletRequest request;

    @Override
    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz) || UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        String email = target instanceof User ?
                ((User) target).getEmail() :
                ((UserTo) target).getEmail();

        try {
            User user = service.getByEmail(email);
            if (isNew() || user.id() != SecurityUtil.authUserId()) {
                String emailErrorCode = "user.email.error.exists";
                errors.rejectValue("email",
                        emailErrorCode,
                        messageSource.getMessage(emailErrorCode, new Object[]{}, request.getLocale()));
            }
        } catch (Exception ignored) {
        }
    }

    private boolean isNew() {
        return (request.getRequestURI().endsWith("/profile/register") ||
                (request.getRequestURI().endsWith("rest/profile") && request.getMethod().equals("POST")));
    }
}
