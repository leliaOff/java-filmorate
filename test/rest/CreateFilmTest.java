package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.validator.CreateFilmValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateFilmTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration( 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyName() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration( 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void longDescription() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description description description description description description description description description description description description description description description description descrip");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration( 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void tooLongDescription() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description description description description description description description description description description description description description description description description description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration( 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void zeroDuration() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration(0);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void negativeDuration() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration(- 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void minimumReleaseDate() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("1895-12-28", formatter));
        request.setDuration( 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void invalidReleaseDate() {
        CreateFilmRequest request = new CreateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("1895-12-27", formatter));
        request.setDuration( 100);
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
