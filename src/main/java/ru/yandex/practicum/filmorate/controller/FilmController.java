package ru.yandex.practicum.filmorate.controller;

import helpers.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;

import java.util.*;

@RestController
@Slf4j
public class FilmController {
    private final HashMap<Long, Film> films = new HashMap<>();
    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }
    @PostMapping
    public Film create(@RequestBody CreateFilmRequest request) {
        Film film = request.parse();
        film.setId(Helper.nextId(films));
        films.put(film.getId(), film);
        log.info(String.format("Фильм добавлен (ID=%d)", film.getId()));
        return film;
    }
    @PutMapping
    public Film update(@RequestBody UpdateFilmRequest request) {
        Film film = request.parse();
        if (!films.containsKey(film.getId())) {
            log.error(String.format("Фильм не найден (ID=%d)", film.getId()));
            throw new NotFoundException("Не найден фильм с указанным идентификатором");
        }
        films.put(film.getId(), film);
        log.info(String.format("Фильм изменен (ID=%d)", film.getId()));
        return film;
    }
}
