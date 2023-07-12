package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;
import static ru.javawebinar.topjava.UserTestData.user;

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DatajpaMealServiceTest extends AbstractMealServiceTest {

    public DatajpaMealServiceTest() {
    }

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(meal1.id(), user.id());
        MEAL_MATCHER.assertMatch(meal1, meal);
        USER_MATCHER.assertMatch(meal.getUser(), user);
    }
}
