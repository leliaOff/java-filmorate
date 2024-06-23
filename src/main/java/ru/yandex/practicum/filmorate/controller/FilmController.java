package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.request.CreateFilmRequest;
import ru.yandex.practicum.filmorate.request.UpdateFilmRequest;
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
    public Collection<FilmDto> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/popular")
    public Collection<FilmDto> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopular(count);
    }

    @GetMapping("/{id}")
    public FilmDto get(@PathVariable long id) {
        return filmService.find(id);
    }

    @PostMapping
    public FilmDto create(@RequestBody CreateFilmRequest request) {
        CreateFilmValidator validator = new CreateFilmValidator(request);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return filmService.create(request);
    }

    @PutMapping
    public FilmDto update(@RequestBody UpdateFilmRequest request) {
        UpdateFilmValidator validator = new UpdateFilmValidator(request);
        validator.validate();
        if (!validator.isValid()) {
            throw new ValidationException("Невалидные параметры", validator.getMessages());
        }
        return filmService.update(request);
    }
//
//    @PutMapping("/{id}/like/{userId}")
//    public Film vote(@PathVariable long id, @PathVariable long userId) {
//        return filmService.vote(id, userId);
//    }
//
//    @DeleteMapping("/{id}/like/{userId}")
//    public Film unvote(@PathVariable long id, @PathVariable long userId) {
//        return filmService.unvote(id, userId);
//    }
}
