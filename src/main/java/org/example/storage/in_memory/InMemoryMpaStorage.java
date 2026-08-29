package org.example.storage.in_memory;

import lombok.RequiredArgsConstructor;
import org.example.model.Mpa;
import org.example.storage.parent.MpaStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("memoryMpaStorage")
@RequiredArgsConstructor
public class InMemoryMpaStorage implements MpaStorage {
    Map<Integer, Mpa> mpaRepository = new HashMap<>();

    @Override
    public List<Mpa> findAll() {
        return mpaRepository.values().stream().toList();
    }

    @Override
    public Optional<Mpa> findById(Integer id) {
        return Optional.ofNullable(mpaRepository.get(id));
    }
}
