package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    @PostMapping("/filter")
    public List<MealTo> getBetween (
            @Nullable @RequestParam LocalDate startDate,
            @Nullable @RequestParam LocalTime startTime,
            @Nullable @RequestParam LocalDate endDate,
            @Nullable @RequestParam LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}