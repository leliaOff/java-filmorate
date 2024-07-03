package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.FilmDto;
import ru.yandex.practicum.filmorate.dal.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    @Qualifier("dbFilmStorage")
    private final FilmStorage storage;

    private final FilmGenreService filmGenreService;

    private final RatingService ratingService;

    private final GenreService genreService;

    @Autowired
    FilmService(@Qualifier("dbFilmStorage") FilmStorage storage, FilmGenreService filmGenreService, RatingService ratingService, GenreService genreService) {
        this.storage = storage;
        this.filmGenreService = filmGenreService;
        this.ratingService = ratingService;
        this.genreService = genreService;
    }

    public Collection<FilmDto> getAll() {
        return storage
                .getAll()
                .stream()
                .map(FilmMapper::mapToFilmDto)
                .peek((FilmDto dto) -> {
                    dto.setGenres(filmGenreService.getGenres(dto.getId()));
                })
                .collect(Collectors.toList());
    }

    public Collection<FilmDto> getPopular(Integer count) {
        return storage
                .getPopular(count)
                .stream()
                .map(FilmMapper::mapToFilmDto)
                .peek((FilmDto dto) -> {
                    dto.setGenres(filmGenreService.getGenres(dto.getId()));
                })
                .collect(Collectors.toList());
    }

    public FilmDto find(Long id) {
        return storage
                .find(id)
                .map(FilmMapper::mapToFilmDto)
                .map((FilmDto dto) -> {
                    dto.setGenres(filmGenreService.getGenres(dto.getId()));
                    return dto;
                })
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    public FilmDto create(CreateFilmRequest request) {
        Film film = FilmMapper.createFilmRequestToFilm(request);

        if (!ratingService.isExist(film.getRating().getId())) {
            throw new ValidationException("Указан несуществующий идентификатор MPA");
        }

        Collection<Genre> requestGenres = GenreMapper.createFilmRequestToGenres(request);

        if (!requestGenres.stream().filter((Genre genre) -> !genreService.isExist(genre.getId())).toList().isEmpty()) {
            throw new ValidationException("Указан несуществующий идентификатор жанра");
        }

        film = storage.create(film).orElseThrow(() -> {
            log.error("Не удалось создать фильм");
            return new InternalServerException("Не удалось создать фильм");
        });
        log.info("Фильм добавлен (ID={})", film.getId());


        Collection<GenreDto> genres = filmGenreService.updateGenres(film.getId(), requestGenres);

        FilmDto dto = FilmMapper.mapToFilmDto(film);
        dto.setGenres(genres);
        return dto;
    }

    public FilmDto update(UpdateFilmRequest request) {
        Long id = request.getId();
        Optional<Film> currentFilm = storage.find(id);
        if (currentFilm.isEmpty()) {
            log.error("Фильм не найден (ID={})", id);
            throw new NotFoundException("Фильм не найден");
        }
        Film film = FilmMapper.updateFilmRequestToFilm(request, currentFilm.get());

        if (!ratingService.isExist(film.getRating().getId())) {
            throw new ValidationException("Указан несуществующий идентификатор MPA");
        }

        Collection<Genre> requestGenres = GenreMapper.updateFilmRequestToGenres(request);

        if (!requestGenres.stream().filter((Genre genre) -> !genreService.isExist(genre.getId())).toList().isEmpty()) {
            throw new ValidationException("Указан несуществующий идентификатор жанра");
        }

        film = storage.update(film).orElseThrow(() -> {
            log.error("Фильм не найден (ID={})", id);
            return new NotFoundException("Фильм не найден");
        });
        log.info("Фильм изменен (ID={})", id);

        Collection<GenreDto> genres = filmGenreService.updateGenres(film.getId(), requestGenres);

        FilmDto dto = FilmMapper.mapToFilmDto(film);
        dto.setGenres(genres);
        return dto;
    }
}
