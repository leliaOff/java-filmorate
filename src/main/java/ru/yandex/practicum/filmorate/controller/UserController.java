package ru.yandex.practicum.filmorate.controller;

import helpers.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;

import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();
    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }
    @PostMapping
    public User create(@RequestBody CreateUserRequest request) {
        User user = request.parse();
        user.setId(Helper.nextId(users));
        users.put(user.getId(), user);
        log.info(String.format("Пользователь добавлен (ID=%d)", user.getId()));
        return user;
    }
    @PutMapping
    public User update(@RequestBody UpdateUserRequest request) {
        User user = request.parse();
        if (!users.containsKey(user.getId())) {
            log.error(String.format("Пользователь не найден (ID=%d)", user.getId()));
            throw new NotFoundException("Не найден пользователь с указанным идентификатором");
        }
        users.put(user.getId(), user);
        log.info(String.format("Пользователь изменен (ID=%d)", user.getId()));
        return user;
    }
}
