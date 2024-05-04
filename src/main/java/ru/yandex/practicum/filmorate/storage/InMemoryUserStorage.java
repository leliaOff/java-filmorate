package ru.yandex.practicum.filmorate.storage;

import helpers.Helper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();

    public Collection<User> getAll() {
        return users.values();
    }

    public User create(User user) {
        user.setId(Helper.nextId(users));
        user.setName(user.getName() != null ? user.getName() : user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Не найден пользователь с указанным идентификатором");
        }
        user.setName(user.getName() != null ? user.getName() : user.getLogin());
        users.put(user.getId(), user);
        return user;
    }
}
