package com.lab.services.http;

import com.lab.services.gateways.UserRepository;
import com.lab.services.models.User;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private Counter getUser = Metrics.counter("users.get");
    private Counter createUser = Metrics.counter("users.find");

    @Timed(value = "users.timer.find", histogram = true, percentiles = {0.9})
    @GetMapping("/{id}")
    public User get(@PathVariable("id") String id) {
        getUser.increment();
        return userRepository.findById(id)
                .orElseGet(User::new);
    }

    @Timed(value = "users.timer.create", histogram = true, percentiles = {0.9})
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@PathVariable("id") String id, @RequestBody User user) {
        createUser.increment();
        user.setId(id);
        userRepository.save(user);
    }
}
