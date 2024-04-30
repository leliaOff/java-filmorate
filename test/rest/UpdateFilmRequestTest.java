package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UpdateFilmRequestTest {
    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Test
    void valid() {
        UpdateFilmRequest request = new UpdateFilmRequest();
        request.setId(Optional.of(1L));
        request.setName(Optional.of("name"));
        request.setDescription(Optional.of("description"));
        request.setReleaseDate(Optional.of("2013-01-01"));
        request.setDuration(Optional.of(100L));
        Film film = new Film();
        film.setId(1L);
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.parse("2013-01-01", formatter));
        film.setDuration(100L);
        try {
            Assertions.assertEquals(request.parse(), film);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void emptyId() {
        UpdateFilmRequest request = new UpdateFilmRequest();
        request.setName(Optional.of("name"));
        request.setDescription(Optional.of("description"));
        request.setReleaseDate(Optional.of("2013-01-01"));
        request.setDuration(Optional.of(100L));

        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать идентификатор фильма", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
}
