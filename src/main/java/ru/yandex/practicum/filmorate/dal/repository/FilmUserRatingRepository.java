package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Repository
public class FilmUserRatingRepository extends BaseRepository<User> {

    public FilmUserRatingRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    /**
     * Список проголосовавших пользователей
     *
     * @param filmId ИД Фильма
     * @return Список
     */
    public Collection<User> getUsers(Long filmId) {
        return get("SELECT users.* FROM film_user_ratings " +
                "JOIN users ON users.id = film_user_ratings.user_id " +
                "WHERE film_user_ratings.film_id = ?", filmId);
    }

    /**
     * Проголосовать
     *
     * @param filmId ИД фильма
     * @param userId ИД пользователя
     */
    public void vote(Long filmId, Long userId) {
        update("MERGE INTO film_user_ratings(film_id, user_id) KEY (film_id, user_id)" +
                "VALUES (?, ?)", filmId, userId);
    }

    /**
     * Отменить голосование
     *
     * @param filmId ИД фильма
     * @param userId ИД пользователя
     */
    public void recall(Long filmId, Long userId) {
        delete("DELETE FROM film_user_ratings " +
                "WHERE film_id = ? " +
                "AND user_id = ?", filmId, userId);
    }
}
