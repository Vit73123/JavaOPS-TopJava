package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int ID_1 = START_SEQ + 3;
    public static final int ID_2 = START_SEQ + 4;
    public static final int ID_3 = START_SEQ + 5;
    public static final int ID_4 = START_SEQ + 6;
    public static final int NOT_FOUND = 10;

    public static final LocalDate START_DATE = LocalDate.of(2020, 1, 30);
    public static final LocalDate END_DATE = LocalDate.of(2020, 1, 30);

    public static final Meal meal_1 = new Meal(ID_1, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal_2 = new Meal(ID_2, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal meal_3 = new Meal(ID_3, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal meal_4 = new Meal(ID_4, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, 6, 28, 10, 0), "newFood", 1555);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal_1);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("UpdatedDescription");
        updated.setCalories(555);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
