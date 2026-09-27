package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.model.Mpa;
import org.example.storage.jpa_repository.MpaRepository;
import org.example.storage.parent.MpaStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository("dbMpaStorage")
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final MpaRepository mpaRepository;

    @Override
    public List<Mpa> findAll() {
        return mpaRepository.findAll();
    }

    @Override
    public Optional<Mpa> findById(Long id) {
        return mpaRepository.findById(id);
    }
}