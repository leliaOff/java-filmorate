package ru.yandex.practicum.filmorate.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseUserRequest {
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
