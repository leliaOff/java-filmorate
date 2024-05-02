package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;


@Slf4j
public class CreateFilmValidator extends AbstractValidator {
    protected final Film film;
    protected transient final LocalDate minDate = LocalDate.of(1895, 12, 28);

    public CreateFilmValidator(Film film) {
        super();
        this.film = film;
    }

    public void validate() {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Пустое название фильма");
            validateResult.add("Необходимо указать название фильма");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.error("Слишком длинное описание фильма");
            validateResult.add("Описание фильма не должно превышать 200 символов");
        }
        if (film.getDuration() != null && film.getDuration() <= 0) {
            log.error("Отрицательная или нулевая продолжительность фильма");
            validateResult.add("Продолжительность фильма должна быть положительным числом");
        }
        if (film.getReleaseDate().isBefore(minDate)) {
            log.error("Дата релиза фильма - до 28 декабря 1895 года");
            validateResult.add("Дата релиза не может быть меньше, чем 28 декабря 1895 года");
        }
    }
}
