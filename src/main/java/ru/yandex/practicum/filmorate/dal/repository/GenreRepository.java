package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Repository
public class GenreRepository extends BaseRepository<Genre> {

    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Genre> get() {
        return get("SELECT * FROM genres");
    }
}
