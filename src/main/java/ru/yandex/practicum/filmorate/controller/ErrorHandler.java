package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.response.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        return new ErrorResponse("Данные не найдены", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final ValidationException e) {
        return new ErrorResponse("Неверные параметры запроса", e.getMessage(), e.getErrors());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка", e.getMessage());
    }
}
