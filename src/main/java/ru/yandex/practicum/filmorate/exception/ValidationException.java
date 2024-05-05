package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ValidationException extends RuntimeException {
    private ArrayList<String> errors;

    public ValidationException(String message, ArrayList<String> errors) {
        super(message);
        this.errors = errors;
    }
}