package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Film;
import org.example.service.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;


    @PostMapping
    public Film create(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    public Film delete(@Valid @RequestBody Film film, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.deleteFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.getFilm(id);
    }

    @GetMapping
    public List<Film> getFilms(HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable Long id, @PathVariable Long userId, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        filmService.like(id, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Long count, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return filmService.getPopularFilms(count);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void dislike(@PathVariable Long id, @PathVariable Long userId, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        filmService.dislike(id, userId);
    }
}
