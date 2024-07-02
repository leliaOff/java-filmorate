package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.Optional;

@Repository
public class RatingRepository extends BaseRepository<Rating> {

    public RatingRepository(JdbcTemplate jdbc, RowMapper<Rating> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Rating> get() {
        return get("SELECT * FROM ratings");
    }

    public Optional<Rating> find(Long id) {
        return find("SELECT * FROM ratings WHERE id = ?", id);
    }
}
