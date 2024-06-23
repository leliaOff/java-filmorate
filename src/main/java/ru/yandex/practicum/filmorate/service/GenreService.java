package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.GenreDto;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenreService {
    @Qualifier("dbGenreStorage")
    private final GenreStorage storage;

    @Autowired
    GenreService(@Qualifier("dbGenreStorage") GenreStorage storage) {
        this.storage = storage;
    }

    public Collection<GenreDto> getAll() {
        return storage
                .getAll()
                .stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toList());
    }
}
