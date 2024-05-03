package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.UpdateFilmValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateFilmTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        Film film = new Film();
        film.setId(1L);
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        UpdateFilmValidator validator = new UpdateFilmValidator(film);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyId() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        UpdateFilmValidator validator = new UpdateFilmValidator(film);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
