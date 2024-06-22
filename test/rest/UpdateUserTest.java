package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;
import ru.yandex.practicum.filmorate.validator.UpdateUserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateUserTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(1L);
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        UpdateUserValidator validator = new UpdateUserValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyId() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        UpdateUserValidator validator = new UpdateUserValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
