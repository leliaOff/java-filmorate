package ru.yandex.practicum.filmorate.dal.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        LocalDate releaseDate = LocalDate.parse(resultSet.getString("release_date"), formatter);
        film.setReleaseDate(releaseDate);
        film.setDuration(resultSet.getInt("duration"));
        Rating rating = new Rating();
        rating.setId(resultSet.getLong("rating_id"));
        rating.setName(resultSet.getString("rating_name"));
        if (rating.getId() != null) {
            film.setRating(rating);
        }
        film.setUserRating(resultSet.getInt("user_rating"));
        return film;
    }
}
