package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    Meal get(Integer id);

    void save(Meal m);

    void update(Meal m);

    void delete(Integer id);

    List<Meal> getAll();
}
