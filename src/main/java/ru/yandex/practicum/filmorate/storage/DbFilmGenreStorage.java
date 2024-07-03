package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.FilmGenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
@Qualifier("dbFilmGenreStorage")
public class DbFilmGenreStorage implements FilmGenreStorage {
    private final FilmGenreRepository filmGenreRepository;

    public DbFilmGenreStorage(FilmGenreRepository filmGenreRepository) {
        this.filmGenreRepository = filmGenreRepository;
    }

    public Collection<Genre> getGenres(Long filmId) {
        return filmGenreRepository.getGenres(filmId);
    }

    public void add(Long filmId, Collection<Genre> genres) {
        for (Genre genre : genres) {
            filmGenreRepository.add(filmId, genre.getId());
        }
    }

    public void remove(Long filmId, Collection<Genre> genres) {
        for (Genre genre : genres) {
            filmGenreRepository.remove(filmId, genre.getId());
        }
    }
}
