package org.example.storage.in_memory;

import lombok.RequiredArgsConstructor;
import org.example.exception.*;
import org.example.model.Film;
import org.example.model.User;
import org.example.storage.parent.FilmStorage;
import org.example.storage.parent.UserStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("memoryFilmStorage")
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    @Qualifier("")
    private final UserStorage userStorage;

    private Integer idCounter = 0;
    private static final LocalDate FILM_MIN_BIRTHDATE = LocalDate.of(1895, 12, 28);
    Map<Integer, Film> filmRepository = new HashMap<>();

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
            throw new UpdateException("Произошла ошибка в обновлении фильма, его не существует");
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
    public Optional<Film> findById(Integer filmId) {
        return Optional.ofNullable(filmRepository.get(filmId));
    }

    @Override
    public void like(Integer filmId, Integer userId) {
        Optional<Film> film = findById(filmId);
        Optional<User> user = userStorage.findById(userId);

        if (film.get().getLikes().contains(user.get().getId())) {
            throw new FilmAlreadyLikedException("Лайк уже поставлен пользователем с ID: " + user.get().getId());
        }

        film.get().getLikes().add(user.get().getId());
    }

    @Override
    public void dislike(Integer filmId, Integer userId) {
        Optional<Film> film = findById(filmId);
        Optional<User> user = userStorage.findById(userId);

        if (!film.get().getLikes().contains(user.get().getId())) {
            throw new FilmAlreadyLikedException("Лайк ранее не был выставлен пользователем с ID: " + user.get().getId());
        }

        film.get().getLikes().remove(user.get().getId());
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        long limit = (count == null || count <= 0) ? 10 : count;
        return filmRepository.values()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(limit)
                .toList();
    }


    private Integer generateId() {
        return ++idCounter;
    }
}
