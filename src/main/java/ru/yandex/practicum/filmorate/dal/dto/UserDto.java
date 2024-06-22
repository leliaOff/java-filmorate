package ru.yandex.practicum.filmorate.dal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    // Друзья
    private Collection<UserDto> friends = new HashSet<>();
    // Подписки
    private Collection<UserDto> subscriptions = new HashSet<>();
    // Подписчики
    private Collection<UserDto> subscribers = new HashSet<>();
}
