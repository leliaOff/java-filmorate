package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UpdateUserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateUserTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        UpdateUserValidator validator = new UpdateUserValidator(user);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyId() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        UpdateUserValidator validator = new UpdateUserValidator(user);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
