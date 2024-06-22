package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class UserFollow {
    private Long id;
    private Long following_user_id;
    private Long followed_user_id;
    private Integer state;
}
