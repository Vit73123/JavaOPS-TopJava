package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMapStorage implements Storage {
    private AtomicInteger newId = new AtomicInteger();

    private Map<Integer, Meal> map = new ConcurrentHashMap<>();

    @Override
    public Meal get(int id) {
        return map.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        if (meal.getId() == null) meal.setId(newId.getAndIncrement());
        map.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        map.put(meal.getId(), meal);
        return meal;
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