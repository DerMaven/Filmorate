package org.example.storage.in_memory;

import lombok.RequiredArgsConstructor;
import org.example.model.Genre;
import org.example.storage.parent.GenreStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository()
@RequiredArgsConstructor
public class InMemoryGenreStorage implements GenreStorage {
    Map<Integer, Genre> genreRepository = new HashMap<>();

    @Override
    public List<Genre> findAll() {
        return genreRepository.values().stream().toList();
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return Optional.ofNullable(genreRepository.get(id));
    }
}
