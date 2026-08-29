package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Film;
import org.example.storage.parent.FilmStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Qualifier("dbFilmStorage")
    private final FilmStorage filmStorage;

    public Film createFilm(Film film) {
        return filmStorage.create(film);
    }

    public Film deleteFilm(Film film) {
        return filmStorage.delete(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Film getFilm(Integer id) {
        return filmStorage.findById(id).get();
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void like(Integer filmId, Integer userId) {
        filmStorage.like(filmId, userId);
    }

    public void dislike(Integer filmId, Integer userId) {
        filmStorage.dislike(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }
}
