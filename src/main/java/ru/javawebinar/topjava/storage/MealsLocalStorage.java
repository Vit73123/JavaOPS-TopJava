package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsLocalStorage implements MealsStorage {
    private AtomicInteger newId = new AtomicInteger();

    private Map<Integer, Meal> map = new ConcurrentHashMap<>();

    @Override
    public Meal get(int id) {
        return map.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(newId.getAndIncrement());
        return map.put(meal.getId(), meal);
    }

    @Override
    public Meal update(Meal meal) {
        return map.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}