package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Slf4j
public class CreateUserRequest {
    private Optional<String> email;
    private Optional<String> login;
    private Optional<String> name;
    private Optional<String> birthday;

    private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private transient final LocalDate maxDate = LocalDate.now();

    public User parse()
    {
        if (email.isEmpty()) {
            log.error("Пустой адрес электронной почты");
            throw new ValidationException("Необходимо указать адрес электронной почты");
        }
        if (email.get().indexOf('@') == -1) {
            log.error("Не валидный адрес электронной почты");
            throw new ValidationException("Необходимо указать валидный адрес электронной почты");
        }
        if (login.isEmpty()) {
            log.error("Пустой логин");
            throw new ValidationException("Необходимо указать логин");
        }
        if (login.get().indexOf(' ') != -1) {
            log.error("Не валидный логин");
            throw new ValidationException("Логин не может содержать пробелы");
        }
        User user = new User();
        user.setEmail(email.get());
        user.setLogin(login.get());
        user.setName(name.orElseGet(() -> login.get()));
        birthday.ifPresent(s -> user.setBirthday(LocalDate.parse(s, formatter)));
        if (user.getBirthday().isAfter(maxDate)) {
            log.error("Не валидная дата рождения");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        return user;
    }
}
