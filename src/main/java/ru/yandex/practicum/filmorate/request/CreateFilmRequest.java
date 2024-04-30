package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Slf4j
public class CreateFilmRequest {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<String> releaseDate;
    private Optional<Long> duration;

    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private transient final LocalDate minDate = LocalDate.of(1895, 12, 28);

    public Film parse()
    {
        if (name.isEmpty()) {
            log.error("Пустое название фильма");
            throw new ValidationException("Необходимо указать название фильма");
        }
        if (description.isPresent() && description.get().length() > 200) {
            log.error("Слишком длинное описание фильма");
            throw new ValidationException("Описание фильма не должно превышать 200 символов");
        }
        if (duration.isPresent() && duration.get() <= 0) {
            log.error("Отрицательная или нулевая продолжительность фильма");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        Film film = new Film();
        film.setName(name.get());
        description.ifPresent(film::setDescription);
        duration.ifPresent(film::setDuration);
        releaseDate.ifPresent(s -> film.setReleaseDate(LocalDate.parse(s, formatter)));
        if (film.getReleaseDate().isBefore(minDate)) {
            log.error("Дата релиза фильма - до 28 декабря 1985 года");
            throw new ValidationException("Дата релиза не может быть меньше, чем 28 декабря 1985 года");
        }
        return film;
    }
}
