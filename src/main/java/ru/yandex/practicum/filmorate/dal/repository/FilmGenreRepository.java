package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Repository
public class FilmGenreRepository extends BaseRepository<Genre> {

    public FilmGenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    /**
     * Список жанров фильма
     *
     * @param filmId ИД Фильма
     * @return Список
     */
    public Collection<Genre> getGenres(Long filmId) {
        return get("SELECT genres.* FROM film_genres " +
                "JOIN genres ON genres.id = film_genres.genre_id " +
                "WHERE film_genres.film_id = ?", filmId);
    }

    /**
     * Добавить жанр фильму
     *
     * @param filmId  ИД Фильма
     * @param genreId ИД Жанра
     */
    public void add(Long filmId, Long genreId) {
        update("MERGE INTO film_genres(film_id, genre_id) KEY (film_id, genre_id)" +
                "VALUES (?, ?)", filmId, genreId);
    }

    /**
     * Удалить жанр фильма
     *
     * @param filmId  ИД Фильма
     * @param genreId ИД Жанра
     */
    public void remove(Long filmId, Long genreId) {
        delete("DELETE FROM film_genres " +
                "WHERE film_id = ? " +
                "AND genre_id = ?", filmId, genreId);
    }
}
