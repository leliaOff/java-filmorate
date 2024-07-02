package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.request.BaseGenreRequest;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreMapper {
    public static GenreDto mapToGenreDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    public static Collection<Genre> createFilmRequestToGenres(CreateFilmRequest request) {
        if (request.getGenres() == null) {
            return new ArrayList<>();
        }
        return request.getGenres()
                .stream()
                .map((BaseGenreRequest genreRequest) -> {
                    Genre genre = new Genre();
                    genre.setId(genreRequest.getId());
                    genre.setName(genreRequest.getName());
                    return genre;
                })
                .collect(Collectors.toList());
    }

    public static Collection<Genre> updateFilmRequestToGenres(UpdateFilmRequest request) {
        if (request.getGenres() == null) {
            return new ArrayList<>();
        }
        return request.getGenres()
                .stream()
                .map((BaseGenreRequest genreRequest) -> {
                    Genre genre = new Genre();
                    genre.setId(genreRequest.getId());
                    genre.setName(genreRequest.getName());
                    return genre;
                })
                .collect(Collectors.toList());
    }
}
