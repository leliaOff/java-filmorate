package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;


@Slf4j
public class CreateFilmValidator extends BaseFilmValidator {
    protected final CreateFilmRequest request;

    public CreateFilmValidator(CreateFilmRequest request) {
        super(request);
        this.request = request;
    }

    public void validate() {
        if (request.getName() == null || request.getName().isBlank()) {
            log.error("Пустое название фильма");
            validateResult.add("Необходимо указать название фильма");
        }
        if (request.getDescription() != null && request.getDescription().length() > 200) {
            log.error("Слишком длинное описание фильма");
            validateResult.add("Описание фильма не должно превышать 200 символов");
        }
        if (request.getDuration() != null && request.getDuration() <= 0) {
            log.error("Отрицательная или нулевая продолжительность фильма");
            validateResult.add("Продолжительность фильма должна быть положительным числом");
        }
        if (request.getReleaseDate() != null && request.getReleaseDate().isBefore(minDate)) {
            log.error("Дата релиза фильма - до 28 декабря 1895 года");
            validateResult.add("Дата релиза не может быть меньше, чем 28 декабря 1895 года");
        }
    }
}
