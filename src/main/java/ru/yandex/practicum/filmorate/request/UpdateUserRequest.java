package ru.yandex.practicum.filmorate.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class UpdateUserRequest extends CreateUserRequest {
    private Optional<Long> id;

    public User parse()
    {
        if (id.isEmpty()) {
            log.error("Не указан идентификатор пользователя");
            throw new ValidationException("Необходимо указать идентификатор пользователя");
        }
        return super.parse();
    }
}
