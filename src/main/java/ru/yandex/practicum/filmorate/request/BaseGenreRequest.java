package ru.yandex.practicum.filmorate.request;

import lombok.Data;

@Data
public class BaseGenreRequest {
    private String name;
    private Long id;
}
