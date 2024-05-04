package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage storage;
    private final UserStorage userStorage;
    private final HashMap<Long, Film> films = new HashMap<>();

    @Autowired
    FilmService(FilmStorage storage, UserStorage userStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
    }

    public Collection<Film> getAll() {
        return storage.getAll();
    }

    public Collection<Film> getBestFilms() {
        return storage.getBestFilms();
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
