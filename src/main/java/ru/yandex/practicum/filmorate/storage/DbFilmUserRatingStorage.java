package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.FilmUserRatingRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component
@Qualifier("dbFilmUserRatingStorage")
public class DbFilmUserRatingStorage implements FilmUserRatingStorage {
    private final FilmUserRatingRepository filmUserRatingRepository;

    public DbFilmUserRatingStorage(FilmUserRatingRepository filmUserRatingRepository) {
        this.filmUserRatingRepository = filmUserRatingRepository;
    }

    public Collection<User> getUsers(Long filmId) {
        return filmUserRatingRepository.getUsers(filmId);
    }

    public void vote(Long filmId, Long userId) {
        filmUserRatingRepository.vote(filmId, userId);
    }

    public void recall(Long filmId, Long userId) {
        filmUserRatingRepository.recall(filmId, userId);
    }
}
