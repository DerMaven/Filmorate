package org.example.storage.parent;

import org.example.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    List<Genre> findAll();
    Optional<Genre> findById(Integer id);
}
