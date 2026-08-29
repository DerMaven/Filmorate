package org.example.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Mpa {
    private Integer id;

    public Mpa(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @NotBlank(message = "Указание рейтинга должно иметься у фильма!")
    private String name;
}
