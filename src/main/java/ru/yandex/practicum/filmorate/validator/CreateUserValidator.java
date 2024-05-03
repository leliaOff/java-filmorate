package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;


@Slf4j
public class CreateUserValidator extends AbstractValidator {
    protected final User user;
    private final transient LocalDate maxDate = LocalDate.now();

    public CreateUserValidator(User user) {
        super();
        this.user = user;
    }

    public void validate() {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("Пустой адрес электронной почты");
            validateResult.add("Необходимо указать адрес электронной почты");
        } else if (user.getEmail().indexOf('@') == -1) {
            log.error("Не валидный адрес электронной почты");
            validateResult.add("Необходимо указать валидный адрес электронной почты");
        }
        if (user.getLogin() == null) {
            log.error("Пустой логин");
            validateResult.add("Необходимо указать логин");
        } else if (user.getLogin().indexOf(' ') != -1) {
            log.error("Не валидный логин");
            validateResult.add("Логин не может содержать пробелы");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(maxDate)) {
            log.error("Не валидная дата рождения");
            validateResult.add("Дата рождения не может быть в будущем");
        }
    }
}
