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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

/*
    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }
*/

    @Override
    public Meal save(Meal meal) {
        if (!isAcceptable(meal, meal.getUserId())) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        // handle case: update, but not present in storage
        repository.put(meal.getId(), meal);
        return meal;
    }

/*
    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }
*/

    @Override
    public boolean delete(int id, int userId) {
        return (isAcceptable(repository.get(id), userId) && repository.remove(id) != null);
    }

/*
    @Override
    public Meal get(int id) {
        return repository.get(id);
    }
*/

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return isAcceptable(meal, userId) ? meal : null;
    }

/*
    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
*/

    @Override
    public Collection<Meal> getAll(int userId) {
        return filterByPredicate(meal -> true);
    }

    public Collection<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return filterByPredicate(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate.plusDays(1)));
    }

    private Collection<Meal> filterByPredicate(Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    private boolean isAcceptable(Meal meal, int userId) {
        return meal != null && meal.getUserId() == userId;
    }
}
