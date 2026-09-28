package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.model.Genre;
import org.example.storage.jpa_repository.GenreRepository;
import org.example.storage.parent.GenreStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository("dbGenreStorage")
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final GenreRepository genreRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }
}