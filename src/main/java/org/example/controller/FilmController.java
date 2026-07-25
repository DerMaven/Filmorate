package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.FilmReleaseDateException;
import org.example.exception.FilmUpdateException;
import org.example.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static final LocalDate FILM_MIN_BIRTHDATE = LocalDate.of(1895, 12, 28);
    List<Film> filmRepository = new ArrayList<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        if (!film.getReleaseDate().isAfter(FILM_MIN_BIRTHDATE)) {
            throw new FilmReleaseDateException("Фильм не может быть выпущен ранее 1895 года");
        }
        filmRepository.add(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        if (!filmRepository.contains(film)) {
            throw new FilmUpdateException("Произошла ошибка в обновлении фильма, его не существует");
        }
        int filmIndex = filmRepository.indexOf(film);
        filmRepository.set(filmIndex, film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms(HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmRepository;
    }
}
