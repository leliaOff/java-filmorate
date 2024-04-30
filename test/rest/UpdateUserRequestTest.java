package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UpdateUserRequestTest {
    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Test
    void valid() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(Optional.of(1L));
        request.setEmail(Optional.of("test@mail.ru"));
        request.setLogin(Optional.of("login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        try {
            Assertions.assertEquals(request.parse(), user);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void emptyId() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail(Optional.of("test@mail.ru"));
        request.setLogin(Optional.of("login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать идентификатор пользователя", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
}
