package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateUserRequestTest {
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Test
    void valid() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday("1990-09-25");
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        try {
            Assertions.assertEquals(request.validate(), user);
        } catch (Throwable exception) {
            String message = exception.getMessage();
            Assertions.fail();
        }
    }
    @Test
    void emptyEmail() {
        CreateUserRequest request = new CreateUserRequest();
        request.setLogin("login");
        request.setName("name");
        request.setBirthday("1990-09-25");
        try {
            request.validate();
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
        request.setEmail("mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday("1990-09-25");
        try {
            request.validate();
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
        request.setEmail("test@mail.ru");
        request.setName("name");
        request.setBirthday("1990-09-25");
        try {
            request.validate();
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
        request.setEmail("test@mail.ru");
        request.setLogin("test login");
        request.setName("name");
        request.setBirthday("1990-09-25");
        try {
            request.validate();
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
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setBirthday("1990-09-25");
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("login");
        user.setName("login");
        user.setBirthday(LocalDate.parse("1990-09-25", formatter));
        try {
            Assertions.assertEquals(request.validate(), user);
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
    @Test
    void invalidBirthday() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@mail.ru");
        request.setLogin("login");
        request.setName("name");
        request.setBirthday("2090-09-25");
        try {
            request.validate();
            Assertions.fail();
        } catch (ValidationException exception) {
            Assertions.assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
        } catch (Throwable exception) {
            Assertions.fail();
        }
    }
}
