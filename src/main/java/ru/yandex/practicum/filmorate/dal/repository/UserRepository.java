package ru.yandex.practicum.filmorate.dal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbc;
    private final UserRowMapper mapper;

    public Collection<User> get() {
        String query = "SELECT * FROM users ORDER BY created_at DESC";
        return jdbc.query(query, mapper);
    }
}
