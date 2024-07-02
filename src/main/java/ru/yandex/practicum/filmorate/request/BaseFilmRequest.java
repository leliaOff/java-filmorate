package ru.yandex.practicum.filmorate.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class BaseFilmRequest {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private BaseMpaRequest mpa;
    private Collection<BaseGenreRequest> genres;
}
