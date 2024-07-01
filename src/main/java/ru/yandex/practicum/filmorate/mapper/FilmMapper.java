package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.FilmDto;
import ru.yandex.practicum.filmorate.dal.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper {
    public static FilmDto mapToFilmDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        if (film.getRating() != null) {
            MpaDto mpaDto = new MpaDto();
            mpaDto.setName(film.getRating().getName());
            mpaDto.setId(film.getRating().getId());
            dto.setMpa(mpaDto);
        }
        dto.setUserRating(film.getUserRating());
        return dto;
    }

    public static Film createFilmRequestToFilm(CreateFilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        if (request.getMpa().getId() != null) {
            Rating rating = new Rating();
            rating.setId(request.getMpa().getId());
            rating.setName(request.getMpa().getName());
            film.setRating(rating);
        }
        return film;
    }

    public static Film updateFilmRequestToFilm(UpdateFilmRequest request, Film currentFilm) {
        Film film = new Film();
        film.setId(request.getId());
        film.setName(request.getName() != null ? request.getName() : currentFilm.getName());
        film.setDescription(request.getDescription() != null ? request.getDescription() : currentFilm.getDescription());
        film.setReleaseDate(request.getReleaseDate() != null ? request.getReleaseDate() : currentFilm.getReleaseDate());
        film.setDuration(request.getDuration() != null ? request.getDuration() : currentFilm.getDuration());
        if (request.getMpa().getId() != null) {
            Rating rating = new Rating();
            rating.setId(request.getMpa().getId());
            rating.setName(request.getMpa().getName());
            film.setRating(rating);
        }
        return film;
    }
}
