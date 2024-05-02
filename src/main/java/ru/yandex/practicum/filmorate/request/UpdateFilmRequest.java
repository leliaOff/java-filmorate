package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.UpdateFilmValidator;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class UpdateFilmRequest extends CreateFilmRequest {
    private Long id;

    public Film parse() {
        Film film = super.parse();
        film.setId(id);
        return film;
    }

    public Film validate() {
        Film film = parse();
        UpdateFilmValidator validator = new UpdateFilmValidator(film);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(validator.getMessage());
        }
        return film;
    }
}
