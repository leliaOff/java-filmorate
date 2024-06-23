package ru.yandex.practicum.filmorate.dal.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.filmorate.exception.InternalServerException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class BaseRepository<T> {
    protected final JdbcTemplate jdbc;
    protected final RowMapper<T> mapper;

    /**
     * Возвращает коллекцию результатов
     *
     * @param query  Запрос
     * @param params Параметры запроса
     * @return       Список
     */
    protected Collection<T> get(String query, Object... params) {
        return jdbc.query(query, mapper, params);
    }

    /**
     * Возвращает один результат
     *
     * @param query  Запрос
     * @param params Параметры запроса
     * @return Строка
     */
    protected Optional<T> find(String query, Object... params) {
        try {
            Collection<T> results = jdbc.query(query, mapper, params);
            if (results.isEmpty()) {
                return Optional.empty();
            }
            return results.stream().findFirst();
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    /**
     * Возвращает количество
     *
     * @param query  Запрос
     * @param params Параметры запроса
     * @return Строка
     */
    protected Integer count(String query, Object... params) {
        try {
            return jdbc.queryForObject(query, Integer.class, params);
        } catch (EmptyResultDataAccessException ignored) {
            return 0;
        }
    }

    protected Long create(String query, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps;
        }, keyHolder);
        try {
            return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("ID");
        } catch (NullPointerException exception) {
            return null;
        }
    }

    protected void update(String query, Object... params) {
        int rows = jdbc.update(query, params);
        if (rows == 0) {
            throw new InternalServerException("Не удалось обновить данные");
        }
    }

    protected void delete(String query, Object... params) {
        int rows = jdbc.update(query, params);
        if (rows == 0) {
            throw new InternalServerException("Не удалось удалить данные");
        }
    }
}
