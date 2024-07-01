package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.request.BaseFilmRequest;

import java.time.LocalDate;


@Slf4j
public class BaseFilmValidator extends AbstractValidator {
    protected final BaseFilmRequest request;
    protected transient LocalDate minDate = LocalDate.of(1895, 12, 28);

    public BaseFilmValidator(BaseFilmRequest request) {
        super();
        this.request = request;
    }

    public void validate() {
    }
}
