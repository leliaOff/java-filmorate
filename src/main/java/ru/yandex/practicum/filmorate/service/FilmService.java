package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    @Qualifier("dbFilmStorage")
    private final FilmStorage storage;
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;

    @Autowired
    FilmService(@Qualifier("dbFilmStorage") FilmStorage storage, @Qualifier("dbUserStorage") UserStorage userStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
    }

    public Collection<FilmDto> getAll() {
        return storage
                .getAll()
                .stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public Collection<FilmDto> getPopular(Integer count) {
        return storage
                .getPopular(count)
                .stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto find(Long id) {
        return storage
                .find(id)
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    public FilmDto create(CreateFilmRequest request) {
        Film film = FilmMapper.createFilmRequestToFilm(request);
        film = storage.create(film).orElseThrow(() -> {
            log.error("Не удалось создать фильм");
            return new InternalServerException("Не удалось создать фильм");
        });
        log.info("Фильм добавлен (ID={})", film.getId());
        return FilmMapper.mapToFilmDto(film);
    }

    public FilmDto update(UpdateFilmRequest request) {
        Long id = request.getId();
        Optional<Film> currentFilm = storage.find(id);
        if (currentFilm.isEmpty()) {
            log.error("Фильм не найден (ID={})", id);
            throw new NotFoundException("Фильм не найден");
        }
        Film film = FilmMapper.updateFilmRequestToFilm(request, currentFilm.get());
        film = storage.update(film).orElseThrow(() -> {
            log.error("Фильм не найден (ID={})", id);
            return new NotFoundException("Фильм не найден");
        });
        log.info("Фильм изменен (ID={})", id);
        return FilmMapper.mapToFilmDto(film);
    }
}
