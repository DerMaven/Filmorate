package org.example.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {

    private Long id;

    @NotBlank(message = "Имя не может быть пустым!")
    private String name;

    @Size(max = 200, message = "Максимальное количество символов: 200")
    private String description;

    private LocalDate releaseDate;

    @Positive(message = "Длительность должна быть положительной")
    private int duration;

    private Set<Long> likes;
}
