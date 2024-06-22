package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Collection<User> getAll();

    Optional<User> find(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> create(User user);

    Optional<User> update(Long id, User user);

    Optional<User> subscribe(User user, User friend);

    Optional<User> unsubscribe(User user, User friend);

    Collection<User> getFriends(User user);

    Collection<User> getCommonFriends(User user, User friend);
}
