package org.example.storage;

import org.example.exception.FilmNotFoundException;
import org.example.exception.FilmReleaseDateException;
import org.example.exception.FilmUpdateException;
import org.example.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film create(Film film);
    public Film update(Film film);
    public Film delete(Film film);
    public List<Film> getFilms();
    public Film findById(Long filmId);
}
