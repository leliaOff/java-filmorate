package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateFilmRequestTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        UpdateFilmRequest request = new UpdateFilmRequest();
        request.setId(1L);
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(100L);
        Film film = new Film();
        film.setId(1L);
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
    void emptyId() {
        UpdateFilmRequest request = new UpdateFilmRequest();
        request.setName("name");
        request.setDescription("description");
        request.setReleaseDate("2013-01-01");
        request.setDuration(100L);

        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать идентификатор фильма", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
}
