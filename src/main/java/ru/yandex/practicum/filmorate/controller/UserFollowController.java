package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserFollowService;
import ru.yandex.practicum.filmorate.service.UserService;

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

    @PutMapping("/{friendId}")
    public UserDto subscribe(@PathVariable long id, @PathVariable long friendId) {
        userFollowService.subscribe(id, friendId);
        return userService.find(id);
    }

//    @DeleteMapping("/{friendId}")
//    public UserDto unsubscribe(@PathVariable long id, @PathVariable long friendId) {
//        return userService.unsubscribe(id, friendId);
//    }
//
//    @GetMapping("/{id}/friends/common/{otherId}")
//    public Collection<UserDto> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
//        return userService.getCommonFriends(id, otherId);
//    }
}
