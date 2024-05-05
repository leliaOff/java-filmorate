package ru.yandex.practicum.filmorate.storage;

import helpers.Helper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();

    public Optional<Film> find(Long id) {
        return this.films.values()
                .stream()
                .filter(film -> film.getId().equals(id))
                .findFirst();
    }

    public Collection<Film> getAll() {
        return films.values();
    }

    public Collection<Film> getPopular(int count) {
        return films.values().stream()
                .sorted((a, b) -> {
                    if (a.getVotes().size() == b.getVotes().size()) {
                        return 0;
                    }
                    return a.getVotes().size() > b.getVotes().size() ? 1 : -1;
                })
                .limit(count)
                .collect(Collectors.toSet());
    }

    public Film create(Film film) {
        film.setId(Helper.nextId(films));
        films.put(film.getId(), film);
        return film;
    }

    public Optional<Film> update(Long id, Film film) {
        if (!films.containsKey(id)) {
            return Optional.empty();
        }
        films.put(id, film);
        return Optional.of(film);
    }

    public Film vote(Film film, User user) {
        film.getVotes().add(user.getId());
        return film;
    }

    public Film unvote(Film film, User user) {
        film.getVotes().remove(user.getId());
        return film;
    }
}
