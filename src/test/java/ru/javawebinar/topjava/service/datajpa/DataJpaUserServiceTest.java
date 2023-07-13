package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User testUser = service.getWithMeals(user);
        USER_MATCHER.assertMatch(testUser, user);
        MEAL_MATCHER.assertMatch(testUser.getMeals(), meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void getWithNoMeals() {
        User testUser = service.getWithMeals(guest);
        USER_MATCHER.assertMatch(testUser, guest);
        MEAL_MATCHER.assertMatch(testUser.getMeals(), noMeals);
    }

    @Test
    public void getWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithMeals(notFoundUser));
    }
}
