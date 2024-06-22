package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.request.BaseUserRequest;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;

import java.time.LocalDate;


@Slf4j
public class BaseUserValidator extends AbstractValidator {
    protected final BaseUserRequest request;
    protected final transient LocalDate maxDate = LocalDate.now();

    public BaseUserValidator(BaseUserRequest request) {
        super();
        this.request = request;
    }

    public void validate() {

    }
}
