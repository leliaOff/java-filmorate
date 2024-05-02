package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;
import ru.yandex.practicum.filmorate.service.UserService;

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
    public User create(@RequestBody CreateUserRequest request) {
        return this.userService.create(request.parse());
    }
    @PutMapping
    public User update(@RequestBody UpdateUserRequest request) {
        return this.userService.update(request.parse());
    }
}
