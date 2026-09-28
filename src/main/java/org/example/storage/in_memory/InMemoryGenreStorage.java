package org.example.storage.in_memory;

import lombok.RequiredArgsConstructor;
import org.example.model.Genre;
import org.example.storage.parent.GenreStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("memoryGenreStorage")
@RequiredArgsConstructor
public class InMemoryGenreStorage implements GenreStorage {
    Map<Long, Genre> genreRepository = new HashMap<>();

    @Override
    public List<Genre> findAll() {
        return genreRepository.values().stream().toList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(genreRepository.get(id));
    }
}
