package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserFollowStorage {
    Collection<User> getFriends(Long id);

    Collection<User> getSubscriptions(Long id);

    Collection<User> getSubscribers(Long id);

    void subscribe(Long id, Long friendId);

    void makeFriend(Long id, Long friendId);

    void leaveSubscription(Long id, Long friendId);

    void unsubscribe(Long id, Long friendId);

    boolean isInitiator(Long id, Long friendId);
}
