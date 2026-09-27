package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.storage.jpa_repository.UserRepository;
import org.example.storage.parent.UserStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository("dbUserStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final UserRepository userRepository;

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        user.getFriends().add(friend);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        user.getFriends().remove(friend);
    }

    @Override
    public List<User> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return user.getFriends().stream().toList();
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherId) {
        return userRepository.getCommonFriends(userId, otherId);
    }
}
