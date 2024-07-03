package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.validator.UpdateFilmValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateFilmTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        UpdateFilmRequest request = new UpdateFilmRequest();
        request.setId(1L);
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration(100);
        UpdateFilmValidator validator = new UpdateFilmValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyId() {
        UpdateFilmRequest request = new UpdateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        request.setDuration(100);
        UpdateFilmValidator validator = new UpdateFilmValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
