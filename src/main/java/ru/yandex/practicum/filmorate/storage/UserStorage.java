package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    Collection<User> getAll();

    Optional<User> find(Long id);

    User create(User user);

    Optional<User> update(Long id, User user);

    User subscribe(User user, User friend);

    User unsubscribe(User user, User friend);

    Set<User> getMutualFriends(User user, User friend);
}
