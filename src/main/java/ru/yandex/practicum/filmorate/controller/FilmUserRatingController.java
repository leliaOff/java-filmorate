package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dal.dto.FilmDto;
import ru.yandex.practicum.filmorate.dal.dto.UserDto;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmUserRatingService;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/films/{id}")
public class FilmUserRatingController {
    private final FilmService filmService;
    private final FilmUserRatingService filmUserRatingService;

    @Autowired
    FilmUserRatingController(FilmService filmService, FilmUserRatingService filmUserRatingService) {
        this.filmService = filmService;
        this.filmUserRatingService = filmUserRatingService;
    }

    @GetMapping("/votes")
    public Collection<UserDto> getUsers(@PathVariable long id) {
        return filmUserRatingService.getUsers(id);
    }

    @GetMapping("/vote/{userId}")
    public FilmDto vote(@PathVariable long id, @PathVariable long userId) {
        filmUserRatingService.vote(id, userId);
        return filmService.find(id);
    }

    @GetMapping("/recall/{userId}")
    public FilmDto recall(@PathVariable long id, @PathVariable long userId) {
        filmUserRatingService.recall(id, userId);
        return filmService.find(id);
    }
}
