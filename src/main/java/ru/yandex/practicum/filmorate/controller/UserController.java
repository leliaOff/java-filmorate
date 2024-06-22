package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;
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
    public Collection<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable long id) {
        return userService.find(id);
    }

    @PostMapping
    public UserDto create(@RequestBody CreateUserRequest request) {
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return userService.create(request);
    }

    @PutMapping
    public UserDto update(@RequestBody UpdateUserRequest request) {
        UpdateUserValidator validator = new UpdateUserValidator(request);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return userService.update(request);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserDto subscribe(@PathVariable long id, @PathVariable long friendId) {
        return userService.subscribe(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserDto unsubscribe(@PathVariable long id, @PathVariable long friendId) {
        return userService.unsubscribe(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<UserDto> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<UserDto> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
