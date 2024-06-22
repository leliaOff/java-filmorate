package ru.yandex.practicum.filmorate.dal.repository;

import helpers.Helper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Collection<User> get() {
        return get("SELECT * FROM users JOIN user_follows");
    }

    public Optional<User> find(Long id) {
        return find("SELECT * FROM users WHERE id = ?", id);
    }

    public Optional<User> findByEmail(String email) {
        return find("SELECT * FROM users WHERE email = ?", email);
    }

    public Optional<User> create(User user) {
        try {
            Long id = create(
                    "INSERT INTO users(email, login, name, birthday) VALUES (?, ?, ?, ?)",
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday().format(Helper.getFormatter())
            );
            user.setId(id);
        } catch (InternalServerException exception) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> update(User user) {
        try {
            update(
                    "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?",
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday().format(Helper.getFormatter()),
                    user.getId()
            );
        } catch (InternalServerException exception) {
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
