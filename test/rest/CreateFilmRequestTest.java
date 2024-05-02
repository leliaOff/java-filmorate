package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateFilmRequestTest {
    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Test
    void valid() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(100L);
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        try {
            Assertions.assertEquals(request.validate(), film);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void emptyName() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setDescription("description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(100L);
        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать название фильма", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void longDescription() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description description description description description description description description description description description description description description description description descrip");
        request.setReleaseDate("2013-01-01");
        request.setDuration(100L);
        try {
            request.validate();
            Assertions.assertTrue(true);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void tooLongDescription() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description description description description description description description description description description description description description description description description description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(100L);
        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Описание фильма не должно превышать 200 символов", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void zeroDuration() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(0L);
        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Продолжительность фильма должна быть положительным числом", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void negativeDuration() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(-100L);
        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Продолжительность фильма должна быть положительным числом", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void minimumReleaseDate() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("1895-12-28");
        request.setDuration(100L);
        try {
            request.validate();
            Assertions.assertTrue(true);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void invalidReleaseDate() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("1895-12-27");
        request.setDuration(100L);
        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Дата релиза не может быть меньше, чем 28 декабря 1895 года", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
}
