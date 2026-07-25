package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.RegistrationException;
import org.example.exception.UpdateException;
import org.example.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    List<User> userRepository = new ArrayList<>();

    @PostMapping
    public User register(@Valid @RequestBody User user, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        if (userRepository.contains(user)) {
            throw new RegistrationException("Данный пользователь уже существует");
        }
        if (user.getName() == null) user.setName(user.getLogin());
        userRepository.add(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user, HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        if (!userRepository.contains(user)) {
            throw new UpdateException("Данного пользователя не существует");
        }
        int userIndex = userRepository.indexOf(user);
        userRepository.set(userIndex, user);
        return user;
    }

    @GetMapping
    public List<User> getUsers(HttpServletRequest request) {
        log.info("Выполнен запрос по эндпоинту: '{} {}', Строка параметров запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return userRepository;
    }
}
