package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapStorage implements Storage {
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private AtomicInteger size = new AtomicInteger();

    @Override
    public Meal get(Integer id) {
        return map.get(id);
    }

    @Override
    public void save(Meal m) {
        m.setId(size.incrementAndGet());
        map.put(m.getId(), m);
    }

    @Override
    public void update(Meal m) {
        map.put(m.getId(), m);
    }

    @Override
    public void delete(Integer id) {
        map.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}