package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface FilmGenreStorage {
    Collection<Genre> getGenres(Long filmId);

    void add(Long filmId, Collection<Genre> genres);

    void remove(Long filmId, Collection<Genre> genres);
}
