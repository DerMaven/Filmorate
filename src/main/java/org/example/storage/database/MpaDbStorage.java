package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.model.Mpa;
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

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAll() {
        String sql = "select * from mpa_ratings order by id";
        return jdbcTemplate.query(sql, MpaDbStorage::mpaRowMapper);
    }

    @Override
    public Optional<Mpa> findById(Integer id) {
        String sql = "select * from mpa_ratings where id = ?";
        List<Mpa> mpas = jdbcTemplate.query(sql, MpaDbStorage::mpaRowMapper, id);
        return mpas.stream().findFirst();
    }

    private static Mpa mpaRowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(
                rs.getInt("id"),
                rs.getString("name"));
    }
}