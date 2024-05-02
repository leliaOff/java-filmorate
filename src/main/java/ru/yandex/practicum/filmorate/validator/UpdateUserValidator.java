package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
public class UpdateUserValidator extends CreateUserValidator {
    public UpdateUserValidator(User user) {
        super(user);
    }

    public void validate() {
        if (user.getId() == null) {
            log.error("Не указан идентификатор фильма");
            validateResult.add("Необходимо указать идентификатор фильма");
        }
        super.validate();
    }
}
