package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.CreateFilmValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Slf4j
public class CreateFilmRequest {
    private String name;
    private String description;
    private String releaseDate;
    private Long duration;

    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Film parse() {
        Film film = new Film();
        film.setName(name);
        if (description != null) {
            film.setDescription(description);
        }
        if (duration != null) {
            film.setDuration(duration);
        }
        if (releaseDate != null) {
            film.setReleaseDate(LocalDate.parse(releaseDate, formatter));
        }
        return film;
    }

    public Film validate() {
        Film film = parse();
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(String.join("\n", validator.getMessages()));
        }
        return film;
    }
}
