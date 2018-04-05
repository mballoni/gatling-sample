package com.lab.services.http;

import com.lab.services.gateways.UserRepository;
import com.lab.services.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .orElseGet(User::new);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        userRepository.save(user);
    }
}
