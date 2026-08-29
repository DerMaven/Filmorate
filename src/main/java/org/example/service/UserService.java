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

    @Qualifier("db")
    private final UserStorage userStorage;

    public User registerUser(User user) {
        return userStorage.register(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public User getUser(Integer id) {
        return userStorage.findById(id).get();
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public void addFriend(Integer userId, Integer friendId) {
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Integer userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
       return userStorage.getCommonFriends(userId, otherId);
    }
}
