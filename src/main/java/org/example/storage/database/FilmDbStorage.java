package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.model.Film;
import org.example.model.Genre;
import org.example.model.Mpa;
import org.example.storage.parent.FilmStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Primary
@Repository("dbFilmStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film create(Film film) {
        String sql = "insert into films (id, uploader_id, title, description, release_date, duration, mpa_id) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                film.getId(),
                film.getUploaderId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpaId());

        saveGenres(film);
        saveLikes(film);
        return findById(film.getId()).orElse(film);
    }

    @Override
    public Film update(Film film) {
        String sql = "update films" +
                " set title = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?" +
                " where id = ?";
        jdbcTemplate.update(sql,
                film.getTitle(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpaId(),
                film.getId());

        jdbcTemplate.update("delete from film_genres where film_id = ?", film.getId());
        saveGenres(film);
        saveLikes(film);
        return findById(film.getId()).orElse(film);
    }

    @Override
    public Film delete(Film film) {
        String sql = "delete from films where id = ?";
        jdbcTemplate.update(sql, film.getId());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "select f.*, m.name as mpa_name from films f" +
                " left join mpa_ratings m on f.mpa_id = m.id";
        List<Film> films = jdbcTemplate.query(sql, FilmDbStorage::filmRowMapper);
        loadGenres(films);
        loadLikes(films);
        return films;
    }

    @Override
    public Optional<Film> findById(Integer filmId) {
        String sql = "select f.*, m.name as mpa_name from films f" +
                " left join mpa_ratings m on f.mpa_id = m.id" +
                " where f.id = ?";
        List<Film> films = jdbcTemplate.query(sql, FilmDbStorage::filmRowMapper, filmId);
        if (films.isEmpty()) {
            return Optional.empty();
        }
        Film film = films.get(0);
        loadGenres(List.of(film));
        loadLikes(List.of(film));
        return Optional.of(film);
    }

    @Override
    public void like(Integer filmId, Integer userId) {
        String sql = "insert into film_likes values (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void dislike(Integer filmId, Integer userId) {
        String sql = "delete from film_likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        String sql = "select f.*, m.name as mpa_name from films f" +
                " left join mpa_ratings m on f.mpa_id = m.id" +
                " left join film_likes fl on f.id = fl.film_id" +
                " group by f.id, m.name" +
                " order by count(fl.user_id) desc" +
                " limit ?";
        List<Film> films = jdbcTemplate.query(sql, FilmDbStorage::filmRowMapper, count);
        loadGenres(films);
        loadLikes(films);
        return films;
    }

    private void saveGenres(Film film) {
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        String sql = "insert into film_genres (film_id, genre_id) values (?, ?)";
        film.getGenres().stream()
                .map(Genre::getId)
                .distinct()
                .forEach(genreId -> jdbcTemplate.update(sql, film.getId(), genreId));
    }

    private void loadGenres(List<Film> films) {
        if (films.isEmpty()) return;
        for (Film film : films) {
            String sql = "select g.id, g.name from film_genres fg" +
                    " join genres g on fg.genre_id = g.id" +
                    " where fg.film_id = ? order by g.id";
            List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Genre(rs.getInt("id"), rs.getString("name")), film.getId());
            film.setGenres(genres);
        }
    }

    private void saveLikes(Film film) {
        if (film.getLikes() == null || film.getLikes().isEmpty()) {
            return;
        }
        String sql = "insert into film_likes (film_id, user_id) values (?, ?)";
        film.getLikes().stream()
                .distinct()
                .forEach(userId -> jdbcTemplate.update(sql, film.getId(), userId));
    }

    private void loadLikes(List<Film> films) {
        if (films.isEmpty()) return;
        for (Film film : films) {
            String sql = "select user_id from film_likes fl where fl.film_id = ?";
            List<Integer> likesList = jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> rs.getObject("user_id", Integer.class),
                    film.getId());
            film.setLikes(new HashSet<>(likesList));
        }
    }

    private static Film filmRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = null;
        if (rs.getObject("mpa_id") != null) {
            mpa = new Mpa(
                    rs.getInt("mpa_id"),
                    rs.getString("mpa_name")
            );
        }
        return new Film(
                rs.getInt("id"),
                rs.getObject("uploader_id") != null ? rs.getInt("uploader_id") : null,
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                mpa.getId()
        );
    }
}