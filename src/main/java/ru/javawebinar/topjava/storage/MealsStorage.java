package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsStorage {
    Meal get(int id);

    Meal create(Meal m);

    Meal update(Meal m);

    void delete(int id);

    List<Meal> getAll();
}
