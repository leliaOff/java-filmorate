package ru.yandex.practicum.filmorate.dal.repository;

import helpers.Helper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Film> get() {
        return get("SELECT films.*, genres.name as genre, ratings.id as rating_id, ratings.name as rating_name, film_user_ratings_count.user_rating as user_rating " +
                "FROM films " +
                "JOIN genres ON genres.id = films.genre_id " +
                "JOIN ratings ON ratings.id = films.rating_id " +
                "LEFT JOIN (SELECT films.id, count(film_user_ratings.film_id) as user_rating " +
                "FROM films " +
                "LEFT JOIN film_user_ratings ON film_user_ratings.film_id = films.id " +
                "GROUP BY films.id) as film_user_ratings_count ON film_user_ratings_count.id = films.id");
    }

    public Collection<Film> getPopular(Integer count) {
        return get("SELECT films.*, genres.name as genre, ratings.id as rating_id, ratings.name as rating_name, film_user_ratings_count.user_rating as user_rating " +
                "FROM films " +
                "JOIN genres ON genres.id = films.genre_id " +
                "JOIN ratings ON ratings.id = films.rating_id " +
                "LEFT JOIN (SELECT films.id, count(film_user_ratings.film_id) as user_rating " +
                "FROM films " +
                "LEFT JOIN film_user_ratings ON film_user_ratings.film_id = films.id " +
                "GROUP BY films.id) as film_user_ratings_count ON film_user_ratings_count.id = films.id " +
                "ORDER BY user_rating DESC " +
                "LIMIT ?", count);
    }

    public Optional<Film> find(Long id) {
        return find("SELECT films.*, genres.name as genre, ratings.id as rating_id, ratings.name as rating_name, film_user_ratings_count.user_rating as user_rating " +
                "FROM films " +
                "JOIN genres ON genres.id = films.genre_id " +
                "JOIN ratings ON ratings.id = films.rating_id " +
                "LEFT JOIN (SELECT films.id, count(film_user_ratings.film_id) as user_rating " +
                "FROM films " +
                "LEFT JOIN film_user_ratings ON film_user_ratings.film_id = films.id " +
                "GROUP BY films.id) as film_user_ratings_count ON film_user_ratings_count.id = films.id " +
                "WHERE films.id = ?", id);
    }

    public Optional<Film> create(Film film) {
        try {
            Long id = create(
                    "INSERT INTO films(name, description, release_date, duration, genre_id, rating_id) VALUES (?, ?, ?, ?, ?, ?)",
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate().format(Helper.getFormatter()),
                    film.getDuration(),
                    film.getGenreId(),
                    film.getRatingId()
            );
            film.setId(id);
        } catch (InternalServerException exception) {
            return Optional.empty();
        }
        return Optional.of(film);
    }

    public Optional<Film> update(Film film) {
        try {
            update(
                    "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, genre_id = ?, rating_id = ? WHERE id = ?",
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate().format(Helper.getFormatter()),
                    film.getDuration(),
                    film.getGenreId(),
                    film.getRatingId(),
                    film.getId()
            );
        } catch (InternalServerException exception) {
            return Optional.empty();
        }
        return this.find(film.getId());
    }
}
