package org.example.storage.parent;

import org.example.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
     Film create(Film film);
     Film update(Film film);
     Film delete(Film film);
     List<Film> getFilms();
     Optional<Film> findById(Long filmId);
     void like(Long filmId, Long userId);
     void dislike(Long filmId, Long userId);
     List<Film> getPopularFilms(Integer count);
}
