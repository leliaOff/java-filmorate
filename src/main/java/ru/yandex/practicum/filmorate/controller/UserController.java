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
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return this.userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        UpdateUserValidator validator = new UpdateUserValidator(user);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return this.userService.update(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return this.userService.find(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User subscribe(
            @PathVariable long id,
            @PathVariable long friendId
    ) {
        return this.userService.subscribe(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User unsubscribe(
            @PathVariable long id,
            @PathVariable long friendId
    ) {
        return this.userService.unsubscribe(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        return this.userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(
            @PathVariable long id,
            @PathVariable long otherId
    ) {
        return this.userService.getCommonFriends(id, otherId);
    }
}
