package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;


@Slf4j
public class CreateUserValidator extends BaseUserValidator {
    protected final CreateUserRequest request;

    public CreateUserValidator(CreateUserRequest request) {
        super(request);
        this.request = request;
    }

    public void validate() {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            log.error("Пустой адрес электронной почты");
            validateResult.add("Необходимо указать адрес электронной почты");
        } else if (request.getEmail().indexOf('@') == -1) {
            log.error("Не валидный адрес электронной почты");
            validateResult.add("Необходимо указать валидный адрес электронной почты");
        }
        if (request.getLogin() == null) {
            log.error("Пустой логин");
            validateResult.add("Необходимо указать логин");
        } else if (request.getLogin().indexOf(' ') != -1) {
            log.error("Не валидный логин");
            validateResult.add("Логин не может содержать пробелы");
        }
        if (request.getBirthday() != null && request.getBirthday().isAfter(maxDate)) {
            log.error("Не валидная дата рождения");
            validateResult.add("Дата рождения не может быть в будущем");
        }
    }
}
