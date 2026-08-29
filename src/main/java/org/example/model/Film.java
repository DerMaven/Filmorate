package org.example.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {

    private Integer id;

    @NotBlank(message = "Название не может быть пустым!")
    private String title;

    @Size(max = 200, message = "Максимальное количество символов: 200")
    private String description;

    private Integer uploaderId;

    private LocalDate releaseDate;

    @Positive(message = "Длительность должна быть положительной")
    private int duration;

    private Mpa mpa;

    private List<Genre> genres = new ArrayList<>();

    private Set<Integer> likes;

    public Film(Integer id, Integer uploaderId, String title, String description, LocalDate releaseDate, int duration, Mpa mpa) {
        this.id = id;
        this.title = title;
        this.uploaderId = uploaderId;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
