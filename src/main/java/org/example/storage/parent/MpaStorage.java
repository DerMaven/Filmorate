package org.example.storage.parent;

import org.example.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    List<Mpa> findAll();
    Optional<Mpa> findById(Long id);
}
