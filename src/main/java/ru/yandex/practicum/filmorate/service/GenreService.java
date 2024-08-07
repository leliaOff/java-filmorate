package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;
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

    public boolean isExist(Long id) {
        Optional<Genre> optional = storage.find(id);
        return optional.isPresent();
    }

    public GenreDto find(Long id) {
        return storage
                .find(id)
                .map(GenreMapper::mapToGenreDto)
                .orElseThrow(() -> new NotFoundException("Жанр не найден"));
    }
}
