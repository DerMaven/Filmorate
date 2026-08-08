package org.example.storage;

import org.example.model.Film;

import java.util.List;

public interface FilmStorage {
     Film create(Film film);
     Film update(Film film);
     Film delete(Film film);
     List<Film> getFilms();
     Film findById(Long filmId);
}
