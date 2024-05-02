package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.CreateUserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Slf4j
public class CreateUserRequest {
    private String email;
    private String login;
    private String name;
    private String birthday;

    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private transient LocalDate maxDate = LocalDate.now();

    public User parse() {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name != null ? name : login);
        if (birthday != null) {
            user.setBirthday(LocalDate.parse(birthday, formatter));
        }
        return user;
    }

    public User validate() {
        User user = parse();
        CreateUserValidator validator = new CreateUserValidator(user);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(String.join("\n", validator.getMessages()));
        }
        return user;
    }
}
