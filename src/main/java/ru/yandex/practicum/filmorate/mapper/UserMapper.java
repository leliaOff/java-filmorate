package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        dto.setBirthday(user.getBirthday());
        return dto;
    }

    public static User createUserRequestToUser(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setLogin(request.getLogin());
        user.setBirthday(request.getBirthday());
        return user;
    }

    public static User updateUserRequestToUser(UpdateUserRequest request, User currentUser) {
        User user = new User();
        user.setId(request.getId() != null ? request.getId() : currentUser.getId());
        user.setEmail(request.getEmail() != null ? request.getEmail() : currentUser.getEmail());
        user.setName(request.getName() != null ? request.getName() : currentUser.getName());
        user.setLogin(request.getLogin() != null ? request.getLogin() : currentUser.getLogin());
        user.setBirthday(request.getBirthday() != null ? request.getBirthday() : currentUser.getBirthday());
        return user;
    }
}
