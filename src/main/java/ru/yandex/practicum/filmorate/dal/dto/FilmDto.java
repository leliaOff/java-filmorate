package ru.yandex.practicum.filmorate.dal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class FilmDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Collection<GenreDto> genres;
    private MpaDto mpa;
    private Integer userRating;
}
