package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.exception.FilmNotFoundException;
import org.example.model.Film;
import org.example.model.Genre;
import org.example.model.Mpa;
import org.example.storage.jpa_repository.FilmRepository;
import org.example.storage.parent.FilmStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Primary
@Repository("dbFilmStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final FilmRepository filmRepository;

    @Override
    public Film create(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film delete(Film film) {
        filmRepository.delete(film);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<Film> findById(Long filmId) {
        return filmRepository.findById(filmId);
    }

    @Override
    public void like(Long filmId, Long userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Фильм не был найден."));
        film.getLikes().add(userId);
        filmRepository.save(film);
    }

    @Override
    public void dislike(Long filmId, Long userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Фильм не был найден."));
        film.getLikes().remove(userId);
        filmRepository.save(film);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        return filmRepository.findPopularFilms(count);
    }
}