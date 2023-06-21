package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.MAX_DATE;
import static ru.javawebinar.topjava.util.DateTimeUtil.MIN_DATE;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1, new ConcurrentHashMap<>());
        repository.put(2, new ConcurrentHashMap<>());
        this.save(MealsUtil.meals.get(0), 1);
        this.save(MealsUtil.meals.get(1), 2);
        this.save(MealsUtil.meals.get(2), 1);
        this.save(MealsUtil.meals.get(3), 2);
        this.save(MealsUtil.meals.get(4), 1);
        this.save(MealsUtil.meals.get(5), 2);
        this.save(MealsUtil.meals.get(6), 1);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        // handle case: update, but not present in storage
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) return null;
        return meals.put(meal.getId(), meal) != null ? meal : null;
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
        return repository.get(userId).values().stream()
                .filter(meal -> (
                        DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate.plusDays(1))))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}
