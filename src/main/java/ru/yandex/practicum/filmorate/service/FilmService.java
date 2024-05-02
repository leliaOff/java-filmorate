package ru.yandex.practicum.filmorate.service;

import helpers.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@Service
public class FilmService {
    private final HashMap<Long, Film> films = new HashMap<>();

    public Collection<Film> getAll() {
        return films.values();
    }

    public Film create(Film film) {
        film.setId(Helper.nextId(films));
        films.put(film.getId(), film);
        log.info("Фильм добавлен (ID={})",  film.getId());
        return film;
    }

    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Фильм не найден (ID={})", film.getId());
            throw new NotFoundException("Не найден фильм с указанным идентификатором");
        }
        films.put(film.getId(), film);
        log.info("Фильм изменен (ID={})", film.getId());
        return film;
    }
}
