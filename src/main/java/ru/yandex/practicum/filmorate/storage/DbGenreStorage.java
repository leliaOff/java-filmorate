package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
@Qualifier("dbGenreStorage")
public class DbGenreStorage implements GenreStorage {
    private final GenreRepository genreRepository;

    public DbGenreStorage(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Collection<Genre> getAll() {
        return genreRepository.get();
    }
}
