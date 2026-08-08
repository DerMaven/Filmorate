package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.SameIDException;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public void addFriend(Long userId, Long friendId) {
        User friend = userStorage.findById(friendId);
        User user = userStorage.findById(userId);

        if (Objects.equals(userId, friendId)) throw new SameIDException("ID пользователей одинаковы");
        if (user.getFriends().contains(friend.getId())) throw new UserAlreadyExistsException("Друг с ID: " + friendId + " уже существует в списке друзей");

        friend.getFriends().add(user.getId());
        user.getFriends().add(friend.getId());
    }

    public void deleteFriend(Long userId, Long friendId) {
        User friend = userStorage.findById(friendId);
        User user = userStorage.findById(userId);

        if (Objects.equals(userId, friendId)) throw new SameIDException("ID пользователей одинаковы");
        if (user.getFriends().contains(friend.getId())) throw new UserAlreadyExistsException("Друг с ID: " + friendId + " уже существует в списке друзей");

        friend.getFriends().remove(user.getId());
        user.getFriends().remove(friend.getId());
    }

    public List<User> getFriends(Long userId) {
        User user = userStorage.findById(userId);
        return user.getFriends()
                .stream()
                .map(userStorage::findById)
                .toList();
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        User user = userStorage.findById(userId);
        User otherUser = userStorage.findById(otherId);

        return user.getFriends().stream()
                .filter(id -> otherUser.getFriends().contains(id))
                .map(userStorage::findById)
                .toList();
    }
}
