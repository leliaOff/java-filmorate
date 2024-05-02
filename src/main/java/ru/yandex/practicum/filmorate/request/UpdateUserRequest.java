package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UpdateUserValidator;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class UpdateUserRequest extends CreateUserRequest {
    private Long id;

    public User parse() {
        User user = super.parse();
        user.setId(id);
        return user;
    }

    public User validate() {
        User user = parse();
        UpdateUserValidator validator = new UpdateUserValidator(user);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(String.join("\n", validator.getMessages()));
        }
        return user;
    }
}
