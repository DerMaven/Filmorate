package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.MpaNotFoundException;
import org.example.model.Mpa;
import org.example.storage.parent.MpaStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {


    @Qualifier("db")
    private final MpaStorage mpaStorage;

    public List<Mpa> getAll() {
        return mpaStorage.findAll();
    }

    public Mpa getById(Integer id) {
        return mpaStorage.findById(id)
                .orElseThrow(() -> new MpaNotFoundException("Mpa с id: " + id + " не найден"));
    }
}