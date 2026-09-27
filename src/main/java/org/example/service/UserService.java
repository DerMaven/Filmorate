package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.storage.parent.UserStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;

    public User registerUser(User user) {
        return userStorage.register(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public User getUser(Long id) {
        return userStorage.findById(id);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public void addFriend(Long userId, Long friendId) {
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
       return userStorage.getCommonFriends(userId, otherId);
    }
}
