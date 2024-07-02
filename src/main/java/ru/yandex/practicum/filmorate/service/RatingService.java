package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.RatingDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.RatingMapper;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.Collection;
import java.util.Optional;
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

    public boolean isExist(Long id) {
        Optional<Rating> optional = storage.find(id);
        return optional.isPresent();
    }

    public RatingDto find(Long id) {
        return storage
                .find(id)
                .map(RatingMapper::mapToRatingDto)
                .orElseThrow(() -> new NotFoundException("MPA не найден"));
    }
}
