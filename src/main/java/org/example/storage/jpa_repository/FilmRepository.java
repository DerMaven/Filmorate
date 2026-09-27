package org.example.storage.jpa_repository;

import org.example.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("select f.*, m.name as mpa_name from films f left join mpa_ratings m on f.mpa_id = m.id left join film_likes fl on f.id = fl.film_id group by f.id, m.name order by count(fl.user_id) desc limit :count")
    List<Film> findPopularFilms(Integer count);
}
