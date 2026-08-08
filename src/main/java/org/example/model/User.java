package org.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {

    private Long id;

    @Email(message = "Неверный формат почты")
    private String email;

    @NotBlank(message = "Логин должен быть указан")
    private String login;

    private String name;

    @Past(message = "День рождения не может быть в будущем")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();
}
