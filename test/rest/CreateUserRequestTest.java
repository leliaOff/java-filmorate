package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CreateUserRequestTest {
    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Test
    void valid() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(Optional.of("test@mail.ru"));
        request.setLogin(Optional.of("login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        User user = new User();
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
    void emptyEmail() {
        CreateUserRequest request = new CreateUserRequest();
        request.setLogin(Optional.of("login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать адрес электронной почты", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void invalidEmail() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(Optional.of("mail.ru"));
        request.setLogin(Optional.of("login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать валидный адрес электронной почты", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void emptyLogin() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(Optional.of("test@mail.ru"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Необходимо указать логин", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void invalidLoginLogin() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(Optional.of("test@mail.ru"));
        request.setLogin(Optional.of("test login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("1990-09-25"));
        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Логин не может содержать пробелы", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void emptyName() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(Optional.of("test@mail.ru"));
        request.setLogin(Optional.of("login"));
        request.setBirthday(Optional.of("1990-09-25"));
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("login");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        try {
            Assertions.assertEquals(request.parse(), user);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void invalidBirthday() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(Optional.of("test@mail.ru"));
        request.setLogin(Optional.of("login"));
        request.setName(Optional.of("name"));
        request.setBirthday(Optional.of("2090-09-25"));
        try {
            request.parse();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
}
