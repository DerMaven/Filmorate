package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.GenreNotFoundException;
import org.example.model.Genre;
import org.example.storage.parent.GenreStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    @Qualifier("dbGenreStorage")
    private final GenreStorage genreStorage;

    public List<Genre> getAll() {
        return genreStorage.findAll();
    }

    public Genre getById(Long id) {
        return genreStorage.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Жанр с id: " + id + " не найден"));
    }
}