package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.MAX_DATE;
import static ru.javawebinar.topjava.util.DateTimeUtil.MIN_DATE;
import static ru.javawebinar.topjava.util.MealsUtil.meals1;
import static ru.javawebinar.topjava.util.MealsUtil.meals2;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1, new ConcurrentHashMap<>());
        repository.put(2, new ConcurrentHashMap<>());
        meals1.forEach(meal -> this.save(meal, 1));
        meals2.forEach(meal -> this.save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (!repository.containsKey(userId)) return null;
        Map<Integer, Meal> meals = repository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (!meals.containsKey(meal.getId())) {  // handle case: update, but not present in storage
            return null;
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getFilteredByDate(MIN_DATE, MAX_DATE, userId);
    }

    public Collection<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        if (!repository.containsKey(userId)) return null;
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) return Collections.emptyList();
        return meals.values().stream()
                .filter(meal -> (
                        DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate.plusDays(1))))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}
