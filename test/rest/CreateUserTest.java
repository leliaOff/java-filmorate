package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.CreateUserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateUserTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyEmail() {
        User user = new User();
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void invalidEmail() {
        User user = new User();
        user.setEmail("mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void emptyLogin() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void invalidLoginLogin() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("test login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void emptyName() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void invalidBirthday() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("2090-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
