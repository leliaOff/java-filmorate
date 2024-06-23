package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
@Qualifier("dbUserStorage")
public class DbUserStorage implements UserStorage {
    private final UserRepository userRepository;

    public DbUserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAll() {
        return userRepository.get();
    }

    public Optional<User> find(Long id) {
        return userRepository.find(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> create(User user) {
        user.setName(user.getName() != null ? user.getName() : user.getLogin());
        return userRepository.create(user);
    }

    public Optional<User> update(Long id, User user) {
        return userRepository.update(user);
    }
}
