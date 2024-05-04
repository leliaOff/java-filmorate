package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    private final UserStorage storage;
    private final HashMap<Long, User> users = new HashMap<>();

    @Autowired
    UserService(UserStorage storage) {
        this.storage = storage;
    }

    public Collection<User> getAll() {
        return storage.getAll();
    }

    public User create(User user) {
        user = this.storage.create(user);
        log.info("Пользователь добавлен (ID={})", user.getId());
        return user;
    }

    public User update(User user) {
        try {
            user = this.storage.update(user);
            log.info("Пользователь изменен (ID={})", user.getId());
            return user;
        } catch (NotFoundException e) {
            log.error("Пользователь не найден (ID={})", user.getId());
            throw new NotFoundException(e.getMessage());
        }
    }
}
