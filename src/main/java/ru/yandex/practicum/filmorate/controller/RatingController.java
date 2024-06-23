package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dal.dto.RatingDto;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public Collection<RatingDto> getAll() {
        return ratingService.getAll();
    }
}
