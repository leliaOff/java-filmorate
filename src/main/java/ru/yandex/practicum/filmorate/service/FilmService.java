package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@Service
public class FilmService {
    @Qualifier("dbFilmStorage")
    private final FilmStorage storage;
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;

    @Autowired
    FilmService(@Qualifier("dbFilmStorage") FilmStorage storage, @Qualifier("dbUserStorage") UserStorage userStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
    }

    public Collection<Film> getAll() {
        return storage.getAll();
    }

    public Collection<Film> getPopular(int count) {
        return storage.getPopular(count);
    }

    public Film find(Long id) {
        return storage.find(id).orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    public Film create(Film film) {
        film = storage.create(film);
        log.info("Фильм добавлен (ID={})", film.getId());
        return film;
    }

    public Film update(Film film) {
        Long id = film.getId();
        film = storage.update(id, film).orElseThrow(() -> {
            log.error("Фильм не найден (ID={})", id);
            return new NotFoundException("Фильм не найден");
        });
        log.info("Фильм изменен (ID={})", id);
        return film;
    }

    public Film vote(Long filmId, Long userId) {
        User user = userStorage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Film film = storage.find(filmId).orElseThrow(() -> new NotFoundException("Фильм не найден"));
        return storage.vote(film, user);
    }

    public Film unvote(Long filmId, Long userId) {
        User user = userStorage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Film film = storage.find(filmId).orElseThrow(() -> new NotFoundException("Фильм не найден"));
        return storage.unvote(film, user);
    }
}
