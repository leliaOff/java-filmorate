package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.validator.CreateUserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateUserTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void valid() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void emptyEmail() {
        CreateUserRequest request = new CreateUserRequest();
        request.setLogin("login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void invalidEmail() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void emptyLogin() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void invalidLoginLogin() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("test login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }

    @Test
    void emptyName() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setBirthday(LocalDate.parse("1990-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertTrue(validator.isValid());
    }

    @Test
    void invalidBirthday() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday(LocalDate.parse("2090-09-25", formatter));
        CreateUserValidator validator = new CreateUserValidator(request);
        validator.validate();
        Assertions.assertFalse(validator.isValid());
    }
}
