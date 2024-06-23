package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmUserRatingStorage {
    Collection<User> getUsers(Long filmId);

    void vote(Long filmId, Long userId);

    void recall(Long filmId, Long userId);
}
