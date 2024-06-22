package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.request.CreateUserRequest;
import ru.yandex.practicum.filmorate.request.UpdateUserRequest;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Qualifier("dbUserStorage")
    private final UserStorage storage;

    @Autowired
    UserService(@Qualifier("dbUserStorage") UserStorage storage) {
        this.storage = storage;
    }

    public Collection<UserDto> getAll() {
        return storage
                .getAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto find(Long id) {
        return storage
                .find(id)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public UserDto create(CreateUserRequest request) {
        Optional<User> o = storage.findByEmail(request.getEmail());
        if (storage.findByEmail(request.getEmail()).isPresent()) {
            log.error("Не удалось создать пользователя: такой email существует");
            throw new InternalServerException("Не удалось создать пользователя: такой email уже существует");
        }
        User user = UserMapper.createUserRequestToUser(request);
        user = storage.create(user).orElseThrow(() -> {
            log.error("Не удалось создать пользователя");
            return new InternalServerException("Не удалось создать пользователя");
        });
        log.info("Пользователь добавлен (ID={})", user.getId());
        return UserMapper.mapToUserDto(user);
    }

    public UserDto update(UpdateUserRequest request) {
        Long id = request.getId();
        Optional<User> duplicateUser = storage.findByEmail(request.getEmail());
        if (duplicateUser.isPresent() && !Objects.equals(duplicateUser.get().getId(), id)) {
            log.error("Не удалось создать пользователя: такой email существует");
            throw new InternalServerException("Не удалось создать пользователя: такой email уже существует");
        }
        Optional<User> currentUser = storage.find(id);
        if (currentUser.isEmpty()) {
            log.error("Пользователь не найден (ID={})", id);
            throw new NotFoundException("Пользователь не найден");
        }
        User user = UserMapper.updateUserRequestToUser(request, currentUser.get());
        user = storage.update(id, user).orElseThrow(() -> {
            log.error("Пользователь не найден (ID={})", id);
            return new NotFoundException("Пользователь не найден");
        });
        log.info("Пользователь изменен (ID={})", id);
        return UserMapper.mapToUserDto(user);
    }

    public UserDto subscribe(Long userId, Long userFriendId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = storage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (Objects.equals(userId, userFriendId)) {
            log.error("Не удалось выполнить запрос: нельзя добавить в друзья самого себя");
            throw new InternalServerException("Не удалось выполнить запрос: нельзя добавить в друзья самого себя");
        }
        user = storage.subscribe(user, friend).orElseThrow(() -> {
            log.error("Не удалось выполнить запрос на добавление в друзья");
            return new NotFoundException("Не удалось выполнить запрос");
        });
        log.info("Запрос на добавление у друзья выполнен");
        return UserMapper.mapToUserDto(user);
    }

    public UserDto unsubscribe(Long userId, Long userFriendId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = storage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        user = storage.unsubscribe(user, friend).orElseThrow(() -> {
            log.error("Не удалось выполнить запрос на удаление из друзей");
            return new NotFoundException("Не удалось выполнить запрос");
        });
        log.info("Запрос на удаление из друзей выполнен");
        return UserMapper.mapToUserDto(user);
    }

    public Collection<UserDto> getFriends(Long userId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return storage
                .getFriends(user)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public Collection<UserDto> getCommonFriends(Long userId, Long userFriendId) {
        User user = storage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = storage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return storage
                .getCommonFriends(user, friend)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
