package org.example.storage;

import lombok.RequiredArgsConstructor;
import org.example.exception.RegistrationException;
import org.example.exception.UpdateException;
import org.example.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private long idCounter = 0;
    Map<Long, User> userRepository = new HashMap<>();

    @Override
    public User register(User user) {
        if (userRepository.containsValue(user)) {
            throw new RegistrationException("Данный пользователь уже существует");
        }
        if (user.getName() == null) user.setName(user.getLogin());
        user.setId(generateId());
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!userRepository.containsKey(user.getId())) {
            throw new UpdateException("Данного пользователя не существует");
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.values().stream().toList();
    }

    private Long generateId() {
        return ++idCounter;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.get(userId);
    }
}
