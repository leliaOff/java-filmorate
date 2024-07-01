package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("dbFilmStorage")
public class DbFilmStorage implements FilmStorage {
    private final FilmRepository filmRepository;

    public DbFilmStorage(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Collection<Film> getAll() {
        return filmRepository.get();
    }

    public Collection<Film> getPopular(Integer count) {
        return filmRepository.getPopular(count);
    }

    public Optional<Film> find(Long id) {
        return filmRepository.find(id);
    }

    public Optional<Film> create(Film film) {
        return filmRepository.create(film);
    }

    public Optional<Film> update(Film film) {
        return filmRepository.update(film);
    }
}
