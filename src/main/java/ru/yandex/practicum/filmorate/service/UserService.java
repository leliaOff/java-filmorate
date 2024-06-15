package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
@Slf4j
public class UserService {
    @Qualifier("dbUserStorage")
    private final UserStorage storage;

    @Autowired
    UserService(@Qualifier("dbUserStorage") UserStorage storage) {
        this.storage = storage;
    }

    public Collection<User> getAll() {
        return storage.getAll();
    }

    public User find(Long id) {
        return storage.find(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public User create(User user) {
        user = this.storage.create(user);
        log.info("Пользователь добавлен (ID={})", user.getId());
        return user;
    }

    public User update(User user) {
        Long id = user.getId();
        user = storage.update(id, user).orElseThrow(() -> {
            log.error("Пользователь не найден (ID={})", id);
            return new NotFoundException("Пользователь не найден");
        });
        log.info("Пользователь изменен (ID={})", id);
        return user;
    }

    public User subscribe(Long userId, Long userFriendId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = storage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return storage.subscribe(user, friend);
    }

    public User unsubscribe(Long userId, Long userFriendId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = storage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return storage.unsubscribe(user, friend);
    }

    public Collection<User> getFriends(Long userId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return storage.getFriends(user);
    }

    public Collection<User> getCommonFriends(Long userId, Long userFriendId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = storage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return storage.getCommonFriends(user, friend);
    }
}
