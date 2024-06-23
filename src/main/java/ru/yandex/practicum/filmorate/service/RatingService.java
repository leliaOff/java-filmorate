package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.RatingDto;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.RatingMapper;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RatingService {
    @Qualifier("dbRatingStorage")
    private final RatingStorage storage;

    @Autowired
    RatingService(@Qualifier("dbRatingStorage") RatingStorage storage) {
        this.storage = storage;
    }

    public Collection<RatingDto> getAll() {
        return storage
                .getAll()
                .stream()
                .map(RatingMapper::mapToRatingDto)
                .collect(Collectors.toList());
    }
}
