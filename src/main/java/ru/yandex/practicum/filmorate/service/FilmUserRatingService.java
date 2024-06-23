package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.FilmUserRatingStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmUserRatingService {
    @Qualifier("dbFilmUserRatingStorage")
    private final FilmUserRatingStorage filmUserRatingStorage;

    @Autowired
    FilmUserRatingService(@Qualifier("dbFilmUserRatingStorage") FilmUserRatingStorage filmUserRatingStorage) {
        this.filmUserRatingStorage = filmUserRatingStorage;
    }

    /**
     * Список проголосовавших пользователей
     *
     * @param filmId ИД фильма
     * @return Список пользователей, которые проголосовали за фильмы
     */
    public Collection<UserDto> getUsers(Long filmId) {
        return filmUserRatingStorage.getUsers(filmId)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Проголосовать за фильм
     *
     * @param filmId ИД фильма
     * @param userId ИД пользователя, который голосует за фильм
     */
    public void vote(Long filmId, Long userId) {
        if (this.getUsers(filmId).stream().anyMatch(user -> user.getId().equals(userId))) {
            log.error("Не удалось выполнить запрос: пользователь уже оставил свой голос за этот фильм");
            throw new InternalServerException("Не удалось выполнить запрос: пользователь уже оставил свой голос за этот фильм");
        }
        filmUserRatingStorage.vote(filmId, userId);
    }

    /**
     * Отозвать голос
     *
     * @param filmId ИД фильма
     * @param userId ИД пользователя, который отзывает голос
     */
    public void recall(Long filmId, Long userId) {
        if (this.getUsers(filmId).stream().filter(user -> user.getId().equals(userId)).findFirst().isEmpty()) {
            log.error("Не удалось выполнить запрос: пользователь не голосовал за фильм");
            throw new InternalServerException("Не удалось выполнить запрос: пользователь не голосовал за фильм");
        }
        filmUserRatingStorage.recall(filmId, userId);
    }
}
