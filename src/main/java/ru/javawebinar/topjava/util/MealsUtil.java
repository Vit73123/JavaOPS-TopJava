package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealsMapStorage;
import ru.javawebinar.topjava.storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    public static final int TEST_CALORIES_PER_DAY = 2000;

    public static final Meal EMPTY = new Meal();

    public static final Meal MEAL_1;
    public static final Meal MEAL_2;
    public static final Meal MEAL_3;
    public static final Meal MEAL_4;
    public static final Meal MEAL_5;
    public static final Meal MEAL_6;
    public static final Meal MEAL_7;

    public static final List<Meal> TEST_MEALS_LIST;
    public static final Storage TEST_MEALS_MAP_STORAGE;

    static {
        MEAL_1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        MEAL_2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        MEAL_3 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        MEAL_4 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        MEAL_5 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        MEAL_6 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        MEAL_7 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

        TEST_MEALS_LIST = new ArrayList<>(Arrays.asList(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7));

        TEST_MEALS_MAP_STORAGE = new MealsMapStorage();
        TEST_MEALS_MAP_STORAGE.create(MEAL_1);
        TEST_MEALS_MAP_STORAGE.create(MEAL_2);
        TEST_MEALS_MAP_STORAGE.create(MEAL_3);
        TEST_MEALS_MAP_STORAGE.create(MEAL_4);
        TEST_MEALS_MAP_STORAGE.create(MEAL_5);
        TEST_MEALS_MAP_STORAGE.create(MEAL_6);
        TEST_MEALS_MAP_STORAGE.create(MEAL_7);
    }

    public static void main(String[] args) {
//        Test Utils on MealsList
        List<Meal> testMealsList = TEST_MEALS_LIST;
        List<MealTo> mealsTo;
        mealsTo = filteredByStreams(testMealsList, LocalTime.of(7, 0), LocalTime.of(12, 0), TEST_CALORIES_PER_DAY);
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(testMealsList, LocalTime.of(7, 0), LocalTime.of(12, 0), TEST_CALORIES_PER_DAY);
        System.out.println();
        mealsTo.forEach(System.out::println);

//        Test Utils on MealsMapStorage
        Storage testMealsMapStorage = TEST_MEALS_MAP_STORAGE;
        mealsTo = getAllMealsTo(testMealsMapStorage.getAll(), TEST_CALORIES_PER_DAY);
        System.out.println();
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(testMealsMapStorage.getAll(), LocalTime.of(7, 0), LocalTime.of(12, 0), TEST_CALORIES_PER_DAY);
        System.out.println();
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> getAllMealsTo(List<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal ->
                        (isBetweenHalfOpen(meal.getTime(), startTime, endTime)))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
