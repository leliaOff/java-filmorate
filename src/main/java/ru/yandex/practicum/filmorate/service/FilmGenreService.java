package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.GenreDto;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmGenreService {
    @Qualifier("dbFilmGenreStorage")
    private final FilmGenreStorage storage;

    @Autowired
    FilmGenreService(@Qualifier("dbFilmGenreStorage") FilmGenreStorage storage) {
        this.storage = storage;
    }

    /**
     * Список жанров фильма
     *
     * @param filmId ИД фильма
     * @return Список жанров
     */
    public Collection<GenreDto> getGenres(Long filmId) {
        return storage.getGenres(filmId)
                .stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toList());
    }

    /**
     * Обновить жанры фильма
     *
     * @param filmId ИД фильма
     * @param genres Список жанров
     * @return Список жанров
     */
    public Collection<GenreDto> updateGenres(Long filmId, Collection<Genre> genres) {
        Collection<Genre> currentGenres = storage.getGenres(filmId);
        Collection<Genre> removedGenres = currentGenres.stream().filter(genres::contains).toList();
        storage.remove(filmId, removedGenres);
        Collection<Genre> insertedGenres = genres.stream().filter((Genre genre) -> !currentGenres.contains(genre)).toList();
        storage.add(filmId, insertedGenres);
        return getGenres(filmId);
    }
}
