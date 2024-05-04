package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    Collection<Film> getAll();

    Collection<Film> getBestFilms();

    Optional<Film> find(Long id);

    Film create(Film film);

    Optional<Film> update(Long id, Film film);

    public Film vote(Film film, User user);

    public Film unvote(Film film, User user);
}
