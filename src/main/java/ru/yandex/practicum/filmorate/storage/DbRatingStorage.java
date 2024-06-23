package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.RatingRepository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;

@Component
@Qualifier("dbRatingStorage")
public class DbRatingStorage implements RatingStorage {
    private final RatingRepository ratingRepository;

    public DbRatingStorage(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Collection<Rating> getAll() {
        return ratingRepository.get();
    }
}
