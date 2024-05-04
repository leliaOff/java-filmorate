package ru.yandex.practicum.filmorate.storage;

import helpers.Helper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();

    public Collection<Film> getAll() {
        return films.values();
    }

    public Film create(Film film) {
        film.setId(Helper.nextId(films));
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Не найден фильм с указанным идентификатором");
        }
        films.put(film.getId(), film);
        return film;
    }
}
