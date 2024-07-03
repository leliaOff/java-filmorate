package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;

@Slf4j
public class UpdateUserValidator extends BaseUserValidator {
    protected final UpdateUserRequest request;

    public UpdateUserValidator(UpdateUserRequest request) {
        super(request);
        this.request = request;
    }

    public void validate() {
        if (request.getId() == null) {
            log.error("Не указан идентификатор пользователя");
            validateResult.add("Необходимо указать идентификатор пользователя");
        }
        if (request.getEmail() != null && request.getEmail().indexOf('@') == -1) {
            log.error("Не валидный адрес электронной почты");
            validateResult.add("Необходимо указать валидный адрес электронной почты");
        }
        if (request.getLogin() != null && request.getLogin().indexOf(' ') != -1) {
            log.error("Не валидный логин");
            validateResult.add("Логин не может содержать пробелы");
        }
        if (request.getBirthday() != null && request.getBirthday().isAfter(maxDate)) {
            log.error("Не валидная дата рождения");
            validateResult.add("Дата рождения не может быть в будущем");
        }
    }
}
