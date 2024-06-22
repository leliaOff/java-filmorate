package ru.yandex.practicum.filmorate.dal.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Repository
public class UserFollowRepository extends BaseRepository<User> {

    public UserFollowRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    /**
     * Список друзей
     *
     * @param id ИД пользователя
     * @return Список
     */
    public Collection<User> getFriends(Long id) {
        return get("SELECT users.* FROM user_follows " +
                "JOIN users ON users.id = user_follows.followed_user_id " +
                "WHERE user_follows.following_user_id = ? AND user_follows.state = 1 " +
                "UNION " +
                "SELECT users.* FROM user_follows " +
                "JOIN users ON users.id = user_follows.following_user_id " +
                "WHERE user_follows.followed_user_id = ? AND user_follows.state = 1", id, id);
    }

    /**
     * Список подписок
     *
     * @param id ИД пользователя
     * @return Список
     */
    public Collection<User> getSubscriptions(Long id) {
        return get("SELECT users.* FROM user_follows " +
                "JOIN users ON users.id = user_follows.followed_user_id " +
                "WHERE user_follows.following_user_id = ? AND user_follows.state = 0", id);
    }

    /**
     * Список подписчиков
     *
     * @param id ИД пользователя
     * @return Список
     */
    public Collection<User> getSubscribers(Long id) {
        return get("SELECT users.* FROM user_follows " +
                "JOIN users ON users.id = user_follows.following_user_id " +
                "WHERE user_follows.followed_user_id = ? AND user_follows.state = 0", id);
    }

    /**
     * Подписаться
     *
     * @param id       ИД пользователя
     * @param friendId ИД пользователя-подписки
     */
    public void subscribe(Long id, Long friendId) {
        update("INSERT INTO user_follows(following_user_id, followed_user_id) " +
                "VALUES (?, ?)", id, friendId);
    }

    /**
     * Подружиться
     *
     * @param id       ИД пользователя
     * @param friendId ИД пользователя-друга
     */
    public void makeFriend(Long id, Long friendId) {
        update("UPDATE user_follows " +
                "SET state = 1 " +
                "WHERE following_user_id = ? " +
                "AND followed_user_id = ?", id, friendId);
    }
}
