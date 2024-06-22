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
import ru.yandex.practicum.filmorate.storage.UserFollowStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserFollowService {
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;
    @Qualifier("dbUserFollowStorage")
    private final UserFollowStorage storage;

    @Autowired
    UserFollowService(@Qualifier("dbUserStorage") UserStorage userStorage, @Qualifier("dbUserFollowStorage") UserFollowStorage storage) {
        this.userStorage = userStorage;
        this.storage = storage;
    }

    /**
     * Список друзей пользователя
     *
     * @param id ИД пользователя
     * @return Список друзей и подписок пользователя
     */
    public Collection<UserDto> getFriends(Long id) {
        return storage.getFriends(id)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Список подписок пользователя
     *
     * @param id ИД пользователя
     * @return Список друзей и подписок пользователя
     */
    public Collection<UserDto> getSubscriptions(Long id) {
        return storage.getSubscriptions(id)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Список подписчиков пользователя
     *
     * @param id ИД пользователя
     * @return Список друзей и подписок пользователя
     */
    public Collection<UserDto> getSubscribers(Long id) {
        return storage.getSubscribers(id)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Отправить запрос на дружбу/принять запрос на дружбу
     *
     * @param userId       ИД пользователя
     * @param userFriendId ИД подписки/друга
     */
    public void subscribe(Long userId, Long userFriendId) {
        if (Objects.equals(userId, userFriendId)) {
            log.error("Не удалось выполнить запрос: нельзя добавить в друзья самого себя");
            throw new InternalServerException("Не удалось выполнить запрос: нельзя добавить в друзья самого себя");
        }
        User user = userStorage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = userStorage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (isFriend(user, friend)) {
            log.error("Не удалось выполнить запрос: пользователи уже являются друзьями");
            throw new InternalServerException("Не удалось выполнить запрос: пользователи уже являются друзьями");
        }

        if (isSubscription(user, friend)) {
            log.error("Не удалось выполнить запрос: подписка была осуществлена ранее");
            throw new InternalServerException("Не удалось выполнить запрос: подписка была осуществлена ранее");
        }

        if (isSubscription(friend, user)) {
            storage.makeFriend(userFriendId, userId);
        } else {
            storage.subscribe(userId, userFriendId);
        }
    }

    /**
     * Отписаться/оставить в подписчиках
     *
     * @param userId       ИД пользователя
     * @param userFriendId ИД подписки/друга
     */
    public void unsubscribe(Long userId, Long userFriendId) {
        if (Objects.equals(userId, userFriendId)) {
            log.error("Не удалось выполнить запрос: нельзя удалить из друзей самого себя");
            throw new InternalServerException("Не удалось выполнить запрос: нельзя удалить из друзей самого себя");
        }
        User user = userStorage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User friend = userStorage.find(userFriendId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (isFriend(user, friend)) {
            if (storage.isInitiator(userId, userFriendId)) {
                storage.unsubscribe(userId, userFriendId);
                storage.subscribe(userFriendId, userId);
            } else {
                storage.leaveSubscription(userFriendId, userId);
            }
            return;
        }

        if (isSubscription(user, friend)) {
            storage.unsubscribe(userId, userFriendId);
            return;
        }

        log.error("Не удалось выполнить запрос: не является другом или подпиской");
        throw new InternalServerException("Не удалось выполнить запрос: не является другом или подпиской");
    }

    /**
     * Список общих друзей
     *
     * @param userId        ИД пользователя
     * @param userOtherId   ИД второго пользователя
     * @return              Список
     */
    public Collection<UserDto> getCommonFriends(Long userId, Long userOtherId) {
        if (Objects.equals(userId, userOtherId)) {
            log.error("Не удалось выполнить запрос: нельзя просмотреть список общих друзей с самим собой");
            throw new InternalServerException("Не удалось выполнить запрос: нельзя просмотреть список общих друзей с самим собой");
        }
        User user = userStorage.find(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        User other = userStorage.find(userOtherId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Collection<UserDto> userFriends = this.getFriends(user.getId());
        Collection<UserDto> otherFriends = this.getFriends(other.getId());

        return userFriends.stream().filter(otherFriends::contains).collect(Collectors.toList());
    }

    /**
     * Являются ли пользователи друзьями
     *
     * @param user       Пользователь
     * @param friendUser Второй пользователь
     * @return ДА/НЕТ
     */
    private boolean isFriend(User user, User friendUser) {
        Collection<UserDto> friends = this.getFriends(user.getId());
        return friends.stream().anyMatch(userDto -> userDto.getId().equals(friendUser.getId()));
    }

    /**
     * Являются ли первый пользователь подписчиком второго
     *
     * @param user       Пользователь
     * @param friendUser Второй пользователь
     * @return ДА/НЕТ
     */
    private boolean isSubscription(User user, User friendUser) {
        Collection<UserDto> subscriptions = this.getSubscriptions(user.getId());
        return subscriptions.stream().anyMatch(userDto -> userDto.getId().equals(friendUser.getId()));
    }
}
