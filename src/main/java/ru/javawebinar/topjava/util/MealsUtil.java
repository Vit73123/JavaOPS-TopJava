package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.Main.*;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    public static void main(String[] args) {
//        Test Utils on Meals
        List<MealTo> mealsTo;
        mealsTo = filteredByStreams(testMealsList, LocalTime.of(7, 0), LocalTime.of(12, 0), TEST_CALORIES_PER_DAY);
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(testMealsList, LocalTime.of(7, 0), LocalTime.of(12, 0), TEST_CALORIES_PER_DAY);
        System.out.println();
        mealsTo.forEach(System.out::println);

//        Test Utils on MapStorage
        mealsTo = getAllMealsTo(testMapStorage.getAll(), TEST_CALORIES_PER_DAY);
        System.out.println();
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(testMapStorage.getAll(), LocalTime.of(7, 0), LocalTime.of(12, 0), TEST_CALORIES_PER_DAY);
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
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }
}
