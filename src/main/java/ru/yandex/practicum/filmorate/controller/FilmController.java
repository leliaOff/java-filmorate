package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validator.CreateFilmValidator;
import ru.yandex.practicum.filmorate.validator.UpdateFilmValidator;

import java.util.Collection;

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
    public Film create(@RequestBody Film film) {
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(validator.getMessage());
        }
        return this.filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        UpdateFilmValidator validator = new UpdateFilmValidator(film);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException(validator.getMessage());
        }
        return this.filmService.update(film);
    }
}
