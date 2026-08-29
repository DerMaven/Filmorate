package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.model.Genre;
import org.example.storage.parent.GenreStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository("dbGenreStorage")
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        String sql = "select * from genres order by id";
        return jdbcTemplate.query(sql, GenreDbStorage::genreRowMapper);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        String sql = "select * from genres where id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, GenreDbStorage::genreRowMapper, id);
        return genres.stream().findFirst();
    }

    private static Genre genreRowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("name"));
    }
}