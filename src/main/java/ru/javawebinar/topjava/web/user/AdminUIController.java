package ru.javawebinar.topjava.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {

    @Override
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User create(@RequestBody User user) {
        user.setRoles(Collections.singleton(Role.USER));
        return super.create(user);
    }

    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(
            @RequestParam boolean enabled,
            @PathVariable int id) {
        log.info("enabled={} with id={}", enabled, id);
        super.enable(enabled, id);
    }
}
