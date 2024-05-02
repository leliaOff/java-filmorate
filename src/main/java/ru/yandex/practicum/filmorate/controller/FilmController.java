package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getAll() {
        return this.filmService.getAll();
    }

    @PostMapping
    public Film create(@RequestBody CreateFilmRequest request) {
        return this.filmService.create(request.validate());
    }

    @PutMapping
    public Film update(@RequestBody UpdateFilmRequest request) {
        return this.filmService.update(request.validate());
    }
}
