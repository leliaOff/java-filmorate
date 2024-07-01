package ru.yandex.practicum.filmorate.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseFilmRequest {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Long genreId;
    private BaseMpaRequest mpa;
}
