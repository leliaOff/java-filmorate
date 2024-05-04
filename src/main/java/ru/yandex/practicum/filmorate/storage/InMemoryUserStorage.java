package ru.yandex.practicum.filmorate.storage;

import helpers.Helper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();

    public Collection<User> getAll() {
        return users.values();
    }

    public Optional<User> find(Long id) {
        return this.users.values()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User create(User user) {
        user.setId(Helper.nextId(users));
        user.setName(user.getName() != null ? user.getName() : user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> update(Long id, User user) {
        if (!users.containsKey(id)) {
            return Optional.empty();
        }
        user.setName(user.getName() != null ? user.getName() : user.getLogin());
        users.put(id, user);
        return Optional.of(user);
    }

    public User subscribe(User user, User friend) {
        user.getFriends().add(friend.getId());
        return user;
    }

    public User unsubscribe(User user, User friend) {
        user.getFriends().remove(friend.getId());
        return user;
    }

    public Set<User> getMutualFriends(User user, User friend) {
        return user.getFriends().stream()
                .filter(id -> friend.getFriends().contains(id))
                .map(this::find)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
