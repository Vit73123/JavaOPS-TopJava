package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam String calories) {
        super.create(new Meal(null, LocalDateTime.parse(dateTime), description, Integer.parseInt(calories)));
    }

    @PostMapping("/filter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setFilter(
            @RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam String endDate,
            @RequestParam String endTime) {
        this.startDate = startDate.isEmpty() ? null : LocalDate.parse(startDate);
        this.startTime = startTime.isEmpty() ? null : LocalTime.parse(startTime);
        this.endDate = endDate.isEmpty() ? null : LocalDate.parse(endDate);
        this.endTime = endTime.isEmpty() ? null : LocalTime.parse(endTime);
    }
}