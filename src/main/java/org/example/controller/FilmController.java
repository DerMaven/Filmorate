package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.FilmReleaseDateException;
import org.example.exception.FilmUpdateException;
import org.example.model.Film;
import org.example.service.FilmService;
import org.example.storage.FilmStorage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private static final LocalDate FILM_MIN_BIRTHDATE = LocalDate.of(1895, 12, 28);
    private final FilmStorage filmStorage;
    private final FilmService filmService;


    @PostMapping
    public Film create(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmStorage.update(film);
    }

    @PutMapping
    @RequestMapping("/{id}/like/{userId}")
    public void like(@PathVariable Long id, @PathVariable Long userId, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        filmService.like(id, userId);
    }

    @PutMapping
    @RequestMapping("/popular?count={count}")
    public List<Film> getFilms(@PathVariable Long count, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.getPopularFilms(count);
    }

    @DeleteMapping
    @RequestMapping("/{id}/like/{userId}")
    public void dislike(@PathVariable Long id, @PathVariable Long userId, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        filmService.dislike(id, userId);
    }
}
