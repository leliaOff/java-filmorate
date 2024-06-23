package ru.yandex.practicum.filmorate.request;

import lombok.Getter;

@Getter
public class UpdateFilmRequest extends BaseFilmRequest {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }
}
