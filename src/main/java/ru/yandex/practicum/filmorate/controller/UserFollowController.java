package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserFollowService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users/{id}/friends")
public class UserFollowController {
    private final UserService userService;
    private final UserFollowService userFollowService;

    @Autowired
    UserFollowController(UserService userService, UserFollowService userFollowService) {
        this.userService = userService;
        this.userFollowService = userFollowService;
    }

    @GetMapping()
    public Collection<UserDto> getFriends(@PathVariable long id) {
        Collection<UserDto> friends = userFollowService.getFriends(id);
        Collection<UserDto> subscriptions = userFollowService.getSubscriptions(id);
        friends.addAll(subscriptions);
        return friends;
    }

    @PutMapping("/{friendId}")
    public UserDto subscribe(@PathVariable long id, @PathVariable long friendId) {
        userFollowService.subscribe(id, friendId);
        return userService.find(id);
    }

    @DeleteMapping("/{friendId}")
    public UserDto unsubscribe(@PathVariable long id, @PathVariable long friendId) {
        userFollowService.unsubscribe(id, friendId);
        return userService.find(id);
    }

    @GetMapping("/common/{otherId}")
    public Collection<UserDto> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userFollowService.getCommonFriends(id, otherId);
    }
}
