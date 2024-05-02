package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validator.CreateUserValidator;
import ru.yandex.practicum.filmorate.validator.UpdateUserValidator;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAll() {
        return this.userService.getAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(validator.getMessage());
        }
        return this.userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        UpdateUserValidator validator = new UpdateUserValidator(user);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(validator.getMessage());
        }
        return this.userService.update(user);
    }
}
