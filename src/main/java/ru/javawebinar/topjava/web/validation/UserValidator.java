package ru.javawebinar.topjava.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserRepository repository;

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
            User user = repository.getByEmail(email);
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
//        Если метод PUT, то независимо от ендпоинта, это update
        return !request.getMethod().equals("PUT");
    }
}
