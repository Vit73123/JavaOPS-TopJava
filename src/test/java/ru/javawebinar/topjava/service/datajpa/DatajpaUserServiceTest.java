package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DatajpaUserServiceTest extends AbstractUserServiceTest {

    public DatajpaUserServiceTest() {
    }

    @Test
    public void getMealsByUser() {
        User testUser = service.getWithMeals(user);
        USER_MATCHER.assertMatch(testUser, user);
        MEAL_MATCHER.assertMatch(testUser.getMeals(), meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }
}
