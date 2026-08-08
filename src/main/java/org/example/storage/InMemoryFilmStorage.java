package org.example.storage;

import lombok.RequiredArgsConstructor;
import org.example.exception.FilmAlreadyExistsException;
import org.example.exception.FilmNotFoundException;
import org.example.exception.FilmReleaseDateException;
import org.example.exception.FilmUpdateException;
import org.example.model.Film;
import org.example.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private long idCounter = 0;
    private static final LocalDate FILM_MIN_BIRTHDATE = LocalDate.of(1895, 12, 28);
    Map<Long, Film> filmRepository = new HashMap<>();

    @Override
    public Film create(Film film) {
        if (!film.getReleaseDate().isAfter(FILM_MIN_BIRTHDATE)) {
            throw new FilmReleaseDateException("Фильм не может быть выпущен ранее 1895 года");
        }
        if (filmRepository.containsValue(film)) {
            throw new FilmAlreadyExistsException("Фильм уже существует в базе данных.");
        }
        film.setId(generateId());
        filmRepository.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!filmRepository.containsValue(film)) {
            throw new FilmUpdateException("Произошла ошибка в обновлении фильма, его не существует");
        }
        filmRepository.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        if (!filmRepository.containsValue(film)) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        filmRepository.remove(film.getId());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return (List<Film>) filmRepository.values();
    }

    @Override
    public Film findById(Long filmId) {
        return filmRepository.get(filmId);
    }

    private Long generateId() {
        return ++idCounter;
    }
}
