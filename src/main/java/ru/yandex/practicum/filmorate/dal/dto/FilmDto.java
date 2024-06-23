package ru.yandex.practicum.filmorate.dal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Data
public class FilmDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private String genre;
    private String rating;
    private Integer userRating;
}
