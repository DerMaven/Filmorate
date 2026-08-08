package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.FilmAlreadyLikedException;
import org.example.model.Film;
import org.example.model.User;
import org.example.storage.FilmStorage;
import org.example.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public void like(Long filmId, Long userId) {
        Film film = filmStorage.findById(filmId);
        User user = userStorage.findById(userId);

        if (film.getLikes().contains(user.getId())) {
            throw new FilmAlreadyLikedException("Лайк уже поставлен пользователем с ID: " + user.getId());
        }

        film.getLikes().add(user.getId());
    }

    public void dislike(Long filmId, Long userId) {
        Film film = filmStorage.findById(filmId);
        User user = userStorage.findById(userId);

        if (!film.getLikes().contains(user.getId())) {
            throw new FilmAlreadyLikedException("Лайк ранее не был выставлен пользователем с ID: " + user.getId());
        }

        film.getLikes().remove(user.getId());
    }

    public List<Film> getPopularFilms(Long count) {
        long limit = (count == null || count <= 0) ? 10 : count;
        return filmStorage.getFilms()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(limit)
                .toList();
    }
}
