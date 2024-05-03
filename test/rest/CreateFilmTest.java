package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.CreateFilmValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateFilmTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyName() {
        Film film = new Film();
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void longDescription() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description description description description description description description description description description description description description description description description descrip");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void tooLongDescription() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description description description description description description description description description description description description description description description description description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void zeroDuration() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(0L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void negativeDuration() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(-100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void minimumReleaseDate() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("1895-12-28", formatter));
        film.setDuration(100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void invalidReleaseDate() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("1895-12-27", formatter));
        film.setDuration(100L);
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
