package ru.yandex.practicum.filmorate.service;

import helpers.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage storage;
    private final HashMap<Long, Film> films = new HashMap<>();
    @Autowired
    FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    public Collection<Film> getAll() {
        return storage.getAll();
    }

    public Film create(Film film) {
        film = storage.create(film);
        log.info("Фильм добавлен (ID={})", film.getId());
        return film;
    }

    public Film update(Film film) {
        try {
            film = storage.update(film);
            log.info("Фильм изменен (ID={})", film.getId());
            return film;
        } catch (NotFoundException e) {
            log.error("Фильм не найден (ID={})", film.getId());
            throw new NotFoundException(e.getMessage());
        }
    }
}
