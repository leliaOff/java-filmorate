package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.UserFollowRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component
@Qualifier("dbUserFollowStorage")
public class DbUserFollowStorage implements UserFollowStorage {
    private final UserFollowRepository userFollowRepository;

    public DbUserFollowStorage(UserFollowRepository userFollowRepository) {
        this.userFollowRepository = userFollowRepository;
    }

    public Collection<User> getFriends(Long id) {
        return userFollowRepository.getFriends(id);
    }

    public Collection<User> getSubscriptions(Long id) {
        return userFollowRepository.getSubscriptions(id);
    }

    public Collection<User> getSubscribers(Long id) {
        return userFollowRepository.getSubscribers(id);
    }

    public void subscribe(Long id, Long friendId) {
        userFollowRepository.subscribe(id, friendId);
    }

    public void makeFriend(Long id, Long friendId) {
        userFollowRepository.makeFriend(id, friendId);
    }

    public void leaveSubscription(Long id, Long friendId) {
        userFollowRepository.leaveSubscription(id, friendId);
    }

    public void unsubscribe(Long id, Long friendId) {
        userFollowRepository.unsubscribe(id, friendId);
    }

    public boolean isInitiator(Long id, Long friendId) {
        return userFollowRepository.isInitiator(id, friendId);
    }
}
