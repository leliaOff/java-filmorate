package ru.yandex.practicum.filmorate.service;

import helpers.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    private final HashMap<Long, User> users = new HashMap<>();

    public Collection<User> getAll() {
        return users.values();
    }

    public User create(User user) {
        user.setId(Helper.nextId(users));
        users.put(user.getId(), user);
        log.info(String.format("Пользователь добавлен (ID=%d)", user.getId()));
        return user;
    }

    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            log.error(String.format("Пользователь не найден (ID=%d)", user.getId()));
            throw new NotFoundException("Не найден пользователь с указанным идентификатором");
        }
        users.put(user.getId(), user);
        log.info(String.format("Пользователь изменен (ID=%d)", user.getId()));
        return user;
    }
}
