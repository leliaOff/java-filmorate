package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    Collection<Film> getAll();

    Collection<Film> getPopular(Integer count);

    Optional<Film> find(Long id);

    Optional<Film> create(Film film);

    Optional<Film> update(Film film);
}
