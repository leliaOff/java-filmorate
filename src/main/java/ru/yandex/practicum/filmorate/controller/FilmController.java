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
        return filmService.getAll();
    }

    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopular(count);
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable long id) {
        return filmService.find(id);
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        CreateFilmValidator validator = new CreateFilmValidator(film);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        UpdateFilmValidator validator = new UpdateFilmValidator(film);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film vote(@PathVariable long id, @PathVariable long userId) {
        return filmService.vote(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film unvote(@PathVariable long id, @PathVariable long userId) {
        return filmService.unvote(id, userId);
    }
}
