package ru.yandex.practicum.filmorate.response;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ErrorResponse {
    private final String error;
    private final String description;
    private final ArrayList<String> messages;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
        messages = new ArrayList<>();
    }

    public ErrorResponse(String error, String description, ArrayList<String> messages) {
        this.error = error;
        this.description = description;
        this.messages = messages;
    }
}
