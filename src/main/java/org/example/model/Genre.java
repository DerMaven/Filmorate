package org.example.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {
    private Integer id;

    @NotBlank(message = "Название жанра должно иметься у фильма!")
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
