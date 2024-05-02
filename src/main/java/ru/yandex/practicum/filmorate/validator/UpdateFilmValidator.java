package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
public class UpdateFilmValidator extends CreateFilmValidator {
    public UpdateFilmValidator(Film film) {
        super(film);
    }

    public void validate() {
        if (film.getId() == null) {
            log.error("Не указан идентификатор фильма");
            validateResult.add("Необходимо указать идентификатор фильма");
        }
        super.validate();
    }
}
