package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.Validator;

import java.util.ArrayList;

abstract public class AbstractValidator {
    protected Validator validateResult;

    public AbstractValidator() {
        this.validateResult = new Validator();
    }

    public ArrayList<String> getMessages() {
        return validateResult.getMessages();
    }

    public boolean isValid() {
        return validateResult.isValid();
    }

    abstract public void validate();
}
