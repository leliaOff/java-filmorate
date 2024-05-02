package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class UpdateFilmRequest extends CreateFilmRequest {
    private Long id;

    public Film parse()
    {
        if (id == null) {
            log.error("Не указан идентификатор фильма");
            throw new ValidationException("Необходимо указать идентификатор фильма");
        }
        Film film = super.parse();
        film.setId(id);
        return film;
    }
}
