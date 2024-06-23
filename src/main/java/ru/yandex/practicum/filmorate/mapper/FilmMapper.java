package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
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
        dto.setGenre(film.getGenre());
        dto.setRating(film.getRating());
        dto.setUserRating(film.getUserRating());
        return dto;
    }

    public static Film createFilmRequestToFilm(CreateFilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        film.setGenreId(request.getGenreId());
        film.setRatingId(request.getRatingId());
        return film;
    }

    public static Film updateFilmRequestToFilm(UpdateFilmRequest request, Film currentFilm) {
        Film film = new Film();
        film.setId(request.getId());
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        film.setGenreId(request.getGenreId());
        film.setRatingId(request.getRatingId());
        return film;
    }
}
