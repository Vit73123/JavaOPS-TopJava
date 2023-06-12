package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import static  ru.javawebinar.topjava.TestData.testMeals;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static void main(String[] args) {
//        List<MealTo> mealsTo = filteredByStreams(testMeals, LocalTime.of(7, 0), LocalTime.of(12, 0));
        List<MealTo> mealsTo = getAllMealsTo(testMeals);
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> getAllMealsTo(List<Meal> meals) {
        return filteredByStreams(meals, null, null);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal ->
                        ((startTime != null && endTime != null) ? isBetweenHalfOpen(meal.getTime(), startTime, endTime) : true))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > MealTo.CALORIES_PER_DAY))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesSumByDate(List<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
