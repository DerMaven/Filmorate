package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "films")
@RequiredArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Название не может быть пустым!")
    @Column(nullable = false)
    private String title;

    @Size(max = 200, message = "Максимальное количество символов: 200")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @DateTimeFormat
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Positive(message = "Длительность должна быть положительной")
    private int duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mpa_id", nullable = false)
    private Mpa mpa;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Genre> genres = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "film_likes",
            joinColumns = @JoinColumn(name = "film_id")
    )
    @Column(name = "user_id")
    private Set<Integer> likes = new HashSet<>();
}
